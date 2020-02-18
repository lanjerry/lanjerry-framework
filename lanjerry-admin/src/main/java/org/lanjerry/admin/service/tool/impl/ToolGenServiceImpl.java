package org.lanjerry.admin.service.tool.impl;

import java.util.Arrays;
import java.util.List;

import org.lanjerry.admin.config.global.ToolGenConfig;
import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.mapper.tool.ToolGenDetailMapper;
import org.lanjerry.admin.mapper.tool.ToolGenMapper;
import org.lanjerry.admin.service.tool.ToolGenDetailService;
import org.lanjerry.admin.service.tool.ToolGenService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.vo.tool.ToolGenDbTableColumnVO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
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
    @Transactional(rollbackFor = Exception.class)
    public void removeGens(Integer[] ids) {
        for (Integer id : ids) {
            ToolGen gen = this.getById(id);
            ApiAssert.notNull(gen, String.format("表编号：%s不存在", id));
            this.removeById(id);

            // 删除数据表字段
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
            toolGen.setModuleName(StrUtil.subSuf(toolGen.getPackageName(), toolGen.getPackageName().lastIndexOf(".") + 1));
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
}