package org.lanjerry.admin.service.tool;

import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.dto.tool.ToolGenUpdateDTO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
import org.lanjerry.admin.vo.tool.ToolGenResultVO;
import org.lanjerry.common.core.entity.tool.ToolGen;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 代码生成业务表 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
public interface ToolGenService extends IService<ToolGen> {

    IPage<ToolGenPageVO> pageGens(ToolGenPageDTO dto);

    ToolGenResultVO getGen(int id);

    void updateGen(int id, ToolGenUpdateDTO dto);

    void removeGens(Integer[] ids);

    IPage<ToolGenDbTableVO> pageDbTables(ToolGenDbTableDTO dto);

    void importDbTables(String[] tableNames);
}
