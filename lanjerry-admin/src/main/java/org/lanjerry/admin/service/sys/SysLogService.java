package org.lanjerry.admin.service.sys;

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
     * @param dto 系统日志列表查询参数
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
    SysLogInfoVO getInfoById(int id);

    /**
     * 删除系统日志
     *
     * @author lanjerry
     * @since 2019/12/19 17:37
     * @param ids 日志编号集
     */
    void removeLogs(Integer[] ids);
}
