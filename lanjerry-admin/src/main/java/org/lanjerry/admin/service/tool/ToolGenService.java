package org.lanjerry.admin.service.tool;

import java.util.List;

import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.dto.tool.ToolGenUpdateDTO;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
import org.lanjerry.admin.vo.tool.ToolGenPreviewVO;
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

    /**
     * 分页查询代码生成业务列表
     *
     * @author lanjerry
     * @since 2020/3/2 11:11
     * @param dto 代码生成业务查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.tool.ToolGenPageVO>
     */
    IPage<ToolGenPageVO> pageGens(ToolGenPageDTO dto);

    /**
     * 根据表编号查询代码生成业务信息
     *
     * @author lanjerry
     * @since 2020/3/2 11:13
     * @param id 表编号
     * @return org.lanjerry.admin.vo.tool.ToolGenResultVO
     */
    ToolGenResultVO getGen(int id);

    /**
     * 更新代码生成业务
     *
     * @author lanjerry
     * @since 2020/3/2 11:14
     * @param id 表编号
     * @param dto 代码生成业务更新参数
     */
    void updateGen(int id, ToolGenUpdateDTO dto);

    /**
     * 删除代码生成业务
     *
     * @author lanjerry
     * @since 2020/3/2 11:21
     * @param ids 表编号数组
     */
    void removeGens(Integer[] ids);

    /**
     * 分页查询数据库表
     *
     * @author lanjerry
     * @since 2020/3/2 11:22
     * @param dto 数据库表查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.tool.ToolGenDbTableVO>
     */
    IPage<ToolGenDbTableVO> pageDbTables(ToolGenDbTableDTO dto);

    /**
     * 导入数据库表
     *
     * @author lanjerry
     * @since 2020/3/2 11:24
     * @param tableNames 数据库表数组
     */
    void importDbTables(String[] tableNames);

    /**
     * 预览代码生成
     *
     * @author lanjerry
     * @since 2020/3/5 18:23
     * @param id 表编号
     * @return java.util.List<org.lanjerry.admin.vo.tool.ToolGenPreviewVO>
     */
    List<ToolGenPreviewVO> preview(int id);

    /**
     * 代码生成
     *
     * @author lanjerry
     * @since 2020/3/2 11:25
     * @param ids 表编号数组
     */
    byte[] code(Integer[] ids);
}
