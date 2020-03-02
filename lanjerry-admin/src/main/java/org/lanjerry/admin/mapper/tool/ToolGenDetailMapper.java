package org.lanjerry.admin.mapper.tool;

import java.util.List;

import org.lanjerry.admin.vo.tool.ToolGenDbTableColumnVO;
import org.lanjerry.common.core.entity.tool.ToolGenDetail;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 代码生成业务明细表 Mapper 接口
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
public interface ToolGenDetailMapper extends BaseMapper<ToolGenDetail> {

    /**
     * 根据表名称查询数据库表的字段列表
     *
     * @author lanjerry
     * @since 2020/3/2 11:36
     * @param tableName 表名称
     * @return java.util.List<org.lanjerry.admin.vo.tool.ToolGenDbTableColumnVO>
     */
    List<ToolGenDbTableColumnVO> selectDbTableColumnsByName(String tableName);
}
