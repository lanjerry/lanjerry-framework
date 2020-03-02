package org.lanjerry.admin.mapper.tool;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.common.core.entity.tool.ToolGen;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 代码生成业务表 Mapper 接口
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
public interface ToolGenMapper extends BaseMapper<ToolGen> {

    IPage<ToolGenDbTableVO> pageDbTables(Page page, @Param("dto")ToolGenDbTableDTO dto);

    List<ToolGenDbTableVO> selectDbTablesByNames(String[] tableNames);
}
