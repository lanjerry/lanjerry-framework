package org.lanjerry.admin.service.tool.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.lanjerry.admin.config.global.ToolGenConfig;
import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.dto.tool.ToolGenUpdateDTO;
import org.lanjerry.admin.mapper.tool.ToolGenDetailMapper;
import org.lanjerry.admin.mapper.tool.ToolGenMapper;
import org.lanjerry.admin.service.tool.ToolGenDetailService;
import org.lanjerry.admin.service.tool.ToolGenService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.util.GeneratorCodeUtil;
import org.lanjerry.admin.vo.tool.ToolGenCodeColumnVO;
import org.lanjerry.admin.vo.tool.ToolGenCodeVO;
import org.lanjerry.admin.vo.tool.ToolGenColumnVO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableColumnVO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenInfoVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
import org.lanjerry.admin.vo.tool.ToolGenResultVO;
import org.lanjerry.common.core.entity.tool.ToolGen;
import org.lanjerry.common.core.entity.tool.ToolGenDetail;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 代码生成业务表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@Service
public class ToolGenServiceImpl extends ServiceImpl<ToolGenMapper, ToolGen> implements ToolGenService {

    @Autowired
    private ToolGenConfig genConfig;

    @Autowired
    private ToolGenDetailService genDetailService;

    @Override
    public IPage<ToolGenPageVO> pageGens(ToolGenPageDTO dto) {
        IPage<ToolGen> page = this.lambdaQuery().orderByDesc(ToolGen::getId)
                .like(StrUtil.isNotBlank(dto.getTableName()), ToolGen::getTableName, dto.getTableName())
                .like(StrUtil.isNotBlank(dto.getTableComment()), ToolGen::getTableComment, dto.getTableComment())
                .ge(StrUtil.isNotBlank(dto.getCreatedTimeStart()), ToolGen::getCreatedTime, dto.getCreatedTimeStart() + AdminConsts.START_TIME)
                .le(StrUtil.isNotBlank(dto.getCreatedTimeEnd()), ToolGen::getCreatedTime, dto.getCreatedTimeEnd() + AdminConsts.END_TIME)
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        return BeanCopyUtil.pageCopy(page, ToolGen.class, ToolGenPageVO.class);
    }

    @Override
    public ToolGenResultVO getGen(int id) {
        ToolGen gen = this.getById(id);
        ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
        ToolGenResultVO result = new ToolGenResultVO();
        ToolGenInfoVO info = BeanCopyUtil.beanCopy(gen, ToolGenInfoVO.class);
        if (StrUtil.isNotBlank(gen.getTplFunction())) {
            info.setTplFunctions(Arrays.asList(gen.getTplFunction().split(",")));
        }
        result.setInfo(info);
        result.setColumns(BeanCopyUtil.listCopy(genDetailService.list(Wrappers.<ToolGenDetail>lambdaQuery().eq(ToolGenDetail::getTableId, id)), ToolGenDetail.class, ToolGenColumnVO.class));
        return result;
    }

    @Override
    public Map<String, String> preview(int id) {
        ToolGen gen = this.getById(id);
        ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
        // 设置基本信息
        ToolGenCodeVO genCode = BeanCopyUtil.beanCopy(gen, ToolGenCodeVO.class);
        if (StrUtil.isNotBlank(gen.getTplFunction())) {
            genCode.setTplFunctions(Arrays.asList(gen.getTplFunction().split(",")));
        }
        // 设置主键信息
        List<ToolGenDetail> details = genDetailService.list(Wrappers.<ToolGenDetail>lambdaQuery().eq(ToolGenDetail::getTableId, id));
        Optional<ToolGenDetail> pkOptional = details.stream().filter(ToolGenDetail::getPkFlag).findFirst();
        if (pkOptional.isPresent()) {
            genCode.setPkComment(pkOptional.get().getColumnComment());
            genCode.setPkJavaType(pkOptional.get().getJavaType());
            genCode.setPkJavaField(pkOptional.get().getJavaField());
            genCode.setPkUpperFirstJavaField(StrUtil.upperFirst(pkOptional.get().getJavaField()));
        }
        // 设置字段信息
        List<ToolGenCodeColumnVO> columns = BeanCopyUtil.listCopy(details, ToolGenDetail.class, ToolGenCodeColumnVO.class);
        columns.forEach(c -> {
            c.setUpperFirstJavaField(StrUtil.upperFirst(c.getJavaField()));
        });
        genCode.setColumns(columns);
        Map<String, String> result = new HashMap<>();

        // 初始化vm模板
        GeneratorCodeUtil.initVelocity();

        // 设置模板信息
        VelocityContext context = GeneratorCodeUtil.setVelocityContext(genCode);

        // 获取模板列表
        List<String> templates = GeneratorCodeUtil.getTemplates(gen.getTplFunction());

        // 渲染模板
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            result.put(template, sw.toString());
        }
        return result;
    }

    @Override
    public byte[] code(Integer[] ids) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (Integer id : ids) {
            generatorCode(id, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGen(int id, ToolGenUpdateDTO dto) {
        ToolGen oriGen = this.getById(id);
        ApiAssert.notNull(oriGen, String.format("表编号：%s不存在", id));
        ToolGen gen = BeanCopyUtil.beanCopy(dto.getInfo(), ToolGen.class);
        gen.setTplFunction(String.join(",", dto.getInfo().getTplFunctions()));
        gen.setId(id);
        this.updateById(gen);

        // 更新明细表
        dto.getColumns().forEach(c -> {
            ToolGenDetail detail = BeanCopyUtil.beanCopy(c, ToolGenDetail.class);
            genDetailService.updateById(detail);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeGens(Integer[] ids) {
        for (Integer id : ids) {
            ToolGen gen = this.getById(id);
            ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
            this.removeById(id);

            // 删除明细表
            genDetailService.remove(Wrappers.<ToolGenDetail>lambdaQuery().eq(ToolGenDetail::getTableId, id));
        }
    }

    @Override
    public IPage<ToolGenDbTableVO> pageDbTables(ToolGenDbTableDTO dto) {
        return this.baseMapper.pageDbTables(new Page<>(dto.getPageNum(), dto.getPageSize()), dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importDbTables(String[] tableNames) {
        ApiAssert.notBlank(Arrays.asList(tableNames), "至少选中一条数据");
        List<ToolGenDbTableVO> tables = this.baseMapper.selectDbTablesByNames(tableNames);
        tables.forEach(t -> {
            // 新增表数据
            String tableName = t.getTableName();
            ToolGen toolGen = new ToolGen();
            toolGen.setTableName(tableName);
            toolGen.setTableComment(t.getTableComment());
            toolGen.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(tableName)));
            toolGen.setPackageName(genConfig.getPackageName());
            toolGen.setModuleName(StrUtil.subPre(tableName, tableName.indexOf("_")));
            toolGen.setBusinessName(StrUtil.subSuf(tableName, tableName.lastIndexOf("_") + 1));
            toolGen.setFunctionName(StrUtil.removeSuffix(t.getTableComment(), "表"));
            toolGen.setFunctionAuthor(genConfig.getAuthor());
            this.save(toolGen);

            // 新增表字段数据
            List<ToolGenDbTableColumnVO> tableColumns = ((ToolGenDetailMapper) genDetailService.getBaseMapper()).selectDbTableColumnsByName(tableName);
            tableColumns.forEach(c -> {
                ToolGenDetail detail = BeanCopyUtil.beanCopy(c, ToolGenDetail.class);
                detail.setTableId(toolGen.getId());
                detail.setJavaType(this.getJavaType(detail.getColumnType()));
                detail.setJavaField(StrUtil.toCamelCase(detail.getColumnName()));
                detail.setFormFlag(false);
                detail.setListFlag(false);
                detail.setQueryFlag(false);
                genDetailService.save(detail);
            });
        });
    }

    /**
     * 根据字段类型转换成java类型
     *
     * @author lanjerry
     * @since 2020/2/23 0:56
     * @param columnType 字段类型
     * @return java.lang.String
     */
    private String getJavaType(String columnType) {
        String dbType = StrUtil.subBefore(columnType, "(", false);
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_STR, dbType)) {
            return AdminConsts.GEN_TYPE_STRING;
        }
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_TIME, dbType)) {
            return AdminConsts.GEN_TYPE_DATE;
        }
        if (ArrayUtil.contains(AdminConsts.GEN_COLUMNTYPE_NUMBER, dbType)) {
            String result;
            String[] dbLength = StrUtil.subBetween(columnType, "(", ")").split(",");
            switch (dbType) {
                case "tinyint":
                    result = "1".equals(dbLength[0]) ? AdminConsts.GEN_TYPE_BOOLEAN : AdminConsts.GEN_TYPE_INTEGER;
                    break;
                case "bigint":
                    result = AdminConsts.GEN_TYPE_LONG;
                    break;
                case "float":
                    result = AdminConsts.GEN_TYPE_FLOAT;
                    break;
                case "double":
                    result = AdminConsts.GEN_TYPE_DOUBLE;
                    break;
                case "decimal":
                    result = AdminConsts.GEN_TYPE_BIGDECIMAL;
                    break;
                default:
                    result = AdminConsts.GEN_TYPE_INTEGER;
                    break;
            }
            return result;
        }
        return dbType;
    }

    /**
     * 代码生成
     *
     * @author lanjerry
     * @since 2020/2/23 1:12
     * @param id 表编号
     * @param zip zip
     */
    private void generatorCode(int id, ZipOutputStream zip) {
        ToolGen gen = this.getById(id);
        ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
        ToolGenCodeVO genCode = BeanCopyUtil.beanCopy(gen, ToolGenCodeVO.class);

        // 初始化vm模板
        GeneratorCodeUtil.initVelocity();

        // 设置模板变量信息
        VelocityContext context = GeneratorCodeUtil.setVelocityContext(genCode);

        // 获取模板列表
        List<String> templates = GeneratorCodeUtil.getTemplates(gen.getTplFunction());

        // 渲染模板
        for (String template : templates) {
            try {
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, "UTF-8");
                tpl.merge(context, sw);
                zip.putNextEntry(new ZipEntry(GeneratorCodeUtil.getFileName(template, gen)));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}