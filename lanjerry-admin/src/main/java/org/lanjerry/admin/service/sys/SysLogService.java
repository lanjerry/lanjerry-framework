package org.lanjerry.admin.service.sys;

import javax.servlet.http.HttpServletResponse;

import org.lanjerry.admin.dto.sys.SysLogExportDTO;
import org.lanjerry.admin.dto.sys.SysLogPageDTO;
import org.lanjerry.admin.vo.sys.SysLogInfoVO;
import org.lanjerry.admin.vo.sys.SysLogPageVO;
import org.lanjerry.common.core.entity.sys.SysLog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统日志表 服务类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页查询系统日志列表
     *
     * @author lanjerry
     * @since 2019/9/5 16:59
     * @param dto 系统日志查询参数
     * @return com.baomidou.mybatisplus.core.metadata.IPage<org.lanjerry.admin.vo.sys.SysLogPageVO>
     */
    IPage<SysLogPageVO> pageLogs(SysLogPageDTO dto);

    /**
     * 根据日志编号查询日志信息
     *
     * @author lanjerry
     * @since 2019/12/19 18:07
     * @param id 日志编号
     * @return org.lanjerry.admin.vo.sys.SysLogInfoVO
     */
    SysLogInfoVO getLog(int id);

    /**
     * 删除系统日志
     *
     * @author lanjerry
     * @since 2019/12/19 17:37
     * @param ids 日志编号数组
     */
    void removeLogs(Integer[] ids);

    /**
     * 导出系统日志
     *
     * @author lanjerry
     * @since 2020/4/14 15:13
     * @param dto 系统日志导出查询DTO
     * @param response 输出流
     */
    void exportLogs(SysLogExportDTO dto, HttpServletResponse response);
}
