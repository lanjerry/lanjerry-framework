package org.lanjerry.admin.service.tool.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import org.lanjerry.admin.service.sys.SysPermissionService;
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
import org.lanjerry.admin.vo.tool.ToolGenPreviewVO;
import org.lanjerry.admin.vo.tool.ToolGenResultVO;
import org.lanjerry.common.core.entity.sys.SysPermission;
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

import cn.hutool.core.collection.CollectionUtil;
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
    private ToolGenDetailService genDetailService;

    @Autowired
    private SysPermissionService permissionService;

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
        result.setColumns(BeanCopyUtil.listCopy(genDetailService.list(Wrappers.<ToolGenDetail>lambdaQuery().orderByAsc(ToolGenDetail::getSort).eq(ToolGenDetail::getTableId, id)), ToolGenDetail.class, ToolGenColumnVO.class));
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGen(int id, ToolGenUpdateDTO dto) {
        ToolGen oriGen = this.getById(id);
        ApiAssert.notNull(oriGen, String.format("表编号：%s不存在", id));
        ApiAssert.isTrue(dto.getInfo().getTplFunctions().stream().filter(c -> CollectionUtil.contains(Arrays.asList("pageList", "list"), c)).count() <= 1, "模板功能的分页列表和列表只能选择一个");
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
            toolGen.setPackageName(ToolGenConfig.packageName);
            toolGen.setModuleName(StrUtil.subPre(tableName, tableName.indexOf("_")));
            toolGen.setBusinessName(StrUtil.toCamelCase(StrUtil.subSuf(tableName, tableName.indexOf("_") + 1)));
            toolGen.setFunctionName(StrUtil.removeSuffix(t.getTableComment(), "表"));
            toolGen.setFunctionAuthor(ToolGenConfig.author);
            this.save(toolGen);

            // 新增表字段数据
            List<ToolGenDbTableColumnVO> tableColumns = ((ToolGenDetailMapper) genDetailService.getBaseMapper()).selectDbTableColumnsByName(tableName);
            tableColumns.forEach(c -> {
                ToolGenDetail detail = BeanCopyUtil.beanCopy(c, ToolGenDetail.class);
                detail.setTableId(toolGen.getId());
                detail.setJavaType(GeneratorCodeUtil.getJavaType(detail.getColumnType()));
                detail.setJavaField(StrUtil.toCamelCase(detail.getColumnName()));
                detail.setFormFlag(false);
                detail.setListFlag(false);
                detail.setQueryFlag(false);
                genDetailService.save(detail);
            });
        });
    }

    @Override
    public List<ToolGenPreviewVO> preview(int id) {
        ToolGenCodeVO genCode = this.initGenCode(id);

        // 设置模板信息
        VelocityContext context = GeneratorCodeUtil.setVelocityContext(genCode);

        // 获取模板列表
        List<String> templates = GeneratorCodeUtil.getTemplates(genCode.getTplFunctions());

        // 渲染模板
        List<ToolGenPreviewVO> result = new ArrayList<>();
        for (String template : templates) {
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);
            ToolGenPreviewVO preview = new ToolGenPreviewVO();
            preview.setTitle(template);
            preview.setContent(sw.toString());
            result.add(preview);
        }
        return result;
    }

    @Override
    public byte[] code(Integer[] ids) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (Integer id : ids) {
            ToolGenCodeVO genCode = this.initGenCode(id);

            // 设置模板变量信息
            VelocityContext context = GeneratorCodeUtil.setVelocityContext(genCode);

            // 获取模板列表
            List<String> templates = GeneratorCodeUtil.getTemplates(genCode.getTplFunctions());

            // 渲染模板
            for (String template : templates) {
                try {
                    StringWriter sw = new StringWriter();
                    Template tpl = Velocity.getTemplate(template, "UTF-8");
                    tpl.merge(context, sw);
                    zip.putNextEntry(new ZipEntry(GeneratorCodeUtil.getFileName(template, genCode)));
                    IOUtils.write(sw.toString(), zip, "UTF-8");
                    IOUtils.closeQuietly(sw);
                    zip.flush();
                    zip.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 初始化代码生成
     *
     * @author lanjerry
     * @since 2020/2/23 1:12
     * @param id 表编号
     */
    private ToolGenCodeVO initGenCode(int id) {
        ToolGen gen = this.getById(id);
        ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
        // 设置基本信息
        ToolGenCodeVO result = BeanCopyUtil.beanCopy(gen, ToolGenCodeVO.class);
        // 设置生成包基本路径
        result.setBasePackage(StrUtil.subBefore(gen.getPackageName(), ".", true));
        // 设置功能名称
        result.setFunctionName(StrUtil.isNotBlank(gen.getFunctionName()) ? gen.getFunctionName() : "【请填写功能名称】");
        if (StrUtil.isNotBlank(gen.getTplFunction())) {
            result.setTplFunctions(Arrays.asList(gen.getTplFunction().split(",")));
        }
        // 设置主键信息
        List<ToolGenDetail> details = genDetailService.list(Wrappers.<ToolGenDetail>lambdaQuery().eq(ToolGenDetail::getTableId, id));
        Optional<ToolGenDetail> pkOptional = details.stream().filter(ToolGenDetail::getPkFlag).findFirst();
        if (pkOptional.isPresent()) {
            result.setPkComment(pkOptional.get().getColumnComment());
            result.setPkJavaType(pkOptional.get().getJavaType());
            result.setPkJavaField(pkOptional.get().getJavaField());
        }
        // 设置字段信息
        List<ToolGenCodeColumnVO> columns = BeanCopyUtil.listCopy(details, ToolGenDetail.class, ToolGenCodeColumnVO.class);
        columns.forEach(c -> {
            // 设置查询方式
            if (c.getQueryFlag()) {
                c.setQueryType(StrUtil.isNotBlank(c.getQueryType()) ? c.getQueryType() : "【请填写查询方式】");
            }
            // 设置字段长度
            c.setColumnLength(StrUtil.subBetween(c.getColumnType(), "(", ")"));
            // 设置JAVA字段名首字母大写
            c.setUpperFirstJavaField(StrUtil.upperFirst(c.getJavaField()));
            // 设置字段例子
            c.setColumnExample(GeneratorCodeUtil.getColumnExample(c));
            // 设置字段查询初始值
            c.setQueryInitValue(GeneratorCodeUtil.getColumnQueryInitValue(c));
            // 设置字段表单初始值
            c.setFormInitValue(GeneratorCodeUtil.getColumnFormInitValue(c));
        });
        result.setColumns(columns);
        // 设置父级权限编号和权限排序
        SysPermission permission = permissionService.getOne(Wrappers.<SysPermission>lambdaQuery().select(SysPermission::getId).eq(SysPermission::getPath, gen.getModuleName()).last("limit 1"));
        if (permission != null) {
            result.setPermissionParentId(permission.getId());
            SysPermission lastPermission = permissionService.getOne(Wrappers.<SysPermission>lambdaQuery().select(SysPermission::getSort).eq(SysPermission::getParentId, permission.getId()).last("order by sort desc limit 1"));
            result.setPermissionSort(lastPermission != null ? lastPermission.getSort() + 10 : 99);
        } else {
            result.setPermissionParentId(0);
            result.setPermissionSort(99);
        }
        // 初始化vm模板
        GeneratorCodeUtil.initVelocity();
        return result;
    }
}