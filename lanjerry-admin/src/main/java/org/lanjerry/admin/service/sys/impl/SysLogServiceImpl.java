package org.lanjerry.admin.service.sys.impl;

import java.util.Arrays;

import org.lanjerry.admin.dto.sys.SysLogPageDTO;
import org.lanjerry.admin.mapper.sys.SysLogMapper;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.vo.sys.SysLogInfoVO;
import org.lanjerry.admin.vo.sys.SysLogPageVO;
import org.lanjerry.common.core.entity.sys.SysLog;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;

/**
 * <p>
 * 系统日志表 服务实现类
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public IPage<SysLogPageVO> pageLogs(SysLogPageDTO dto) {
        IPage<SysLog> page = this.lambdaQuery().orderByDesc(SysLog::getId)
                .like(StrUtil.isNotBlank(dto.getUserAccount()), SysLog::getUserAccount, dto.getUserAccount())
                .like(StrUtil.isNotBlank(dto.getIpAddress()), SysLog::getIpAddress, dto.getIpAddress())
                .like(StrUtil.isNotBlank(dto.getRequestUri()), SysLog::getRequestUri, dto.getRequestUri())
                .like(StrUtil.isNotBlank(dto.getRequestMethod()), SysLog::getRequestMethod, dto.getRequestMethod())
                .like(StrUtil.isNotBlank(dto.getActionName()), SysLog::getActionName, dto.getActionName())
                .eq(dto.getStatus() != null, SysLog::getStatus, dto.getStatus())
                .ge(StrUtil.isNotBlank(dto.getCreatedTimeStart()), SysLog::getCreatedTime, dto.getCreatedTimeStart() + AdminConsts.START_TIME)
                .le(StrUtil.isNotBlank(dto.getCreatedTimeEnd()), SysLog::getCreatedTime, dto.getCreatedTimeEnd() + AdminConsts.END_TIME)
                .page(new Page<>(dto.getPageNum(), dto.getPageSize()));
        return BeanCopyUtil.pageCopy(page, SysLog.class, SysLogPageVO.class);
    }

    @Override
    public SysLogInfoVO getLog(int id) {
        SysLog oriLog = this.getById(id);
        ApiAssert.notNull(oriLog, String.format("日志编号：%s不存在", id));
        return BeanCopyUtil.beanCopy(oriLog, SysLogInfoVO.class);
    }

    @Override
    public void removeLogs(Integer[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }
}
