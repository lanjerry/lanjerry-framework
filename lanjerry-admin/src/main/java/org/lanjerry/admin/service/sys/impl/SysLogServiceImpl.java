package org.lanjerry.admin.service.sys.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.lanjerry.admin.dto.sys.SysLogExportDTO;
import org.lanjerry.admin.dto.sys.SysLogPageDTO;
import org.lanjerry.admin.mapper.sys.SysLogMapper;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.admin.util.AdminConsts;
import org.lanjerry.admin.vo.sys.SysLogExportVO;
import org.lanjerry.admin.vo.sys.SysLogInfoVO;
import org.lanjerry.admin.vo.sys.SysLogPageVO;
import org.lanjerry.common.core.entity.sys.SysLog;
import org.lanjerry.common.core.util.ApiAssert;
import org.lanjerry.common.core.util.BeanCopyUtil;
import org.springframework.stereotype.Service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.collection.CollUtil;
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

    @Override
    public void exportLogs(SysLogExportDTO dto, HttpServletResponse response) {
        List<SysLog> list = this.lambdaQuery().orderByDesc(SysLog::getId)
                .in(CollUtil.isNotEmpty(dto.getIds()), SysLog::getId, dto.getIds())
                .like(StrUtil.isNotBlank(dto.getUserAccount()), SysLog::getUserAccount, dto.getUserAccount())
                .like(StrUtil.isNotBlank(dto.getIpAddress()), SysLog::getIpAddress, dto.getIpAddress())
                .like(StrUtil.isNotBlank(dto.getRequestUri()), SysLog::getRequestUri, dto.getRequestUri())
                .like(StrUtil.isNotBlank(dto.getRequestMethod()), SysLog::getRequestMethod, dto.getRequestMethod())
                .like(StrUtil.isNotBlank(dto.getActionName()), SysLog::getActionName, dto.getActionName())
                .eq(dto.getStatus() != null, SysLog::getStatus, dto.getStatus())
                .ge(StrUtil.isNotBlank(dto.getCreatedTimeStart()), SysLog::getCreatedTime, dto.getCreatedTimeStart() + AdminConsts.START_TIME)
                .le(StrUtil.isNotBlank(dto.getCreatedTimeEnd()), SysLog::getCreatedTime, dto.getCreatedTimeEnd() + AdminConsts.END_TIME)
                .list();
        List<SysLogExportVO> result = new ArrayList<>();
        list.forEach(l -> {
            SysLogExportVO logExport = BeanCopyUtil.beanCopy(l, SysLogExportVO.class);
            logExport.setStatus(l.getStatus().getText());
            logExport.setCreatedTime(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(l.getCreatedTime()));
            result.add(logExport);
        });

        // easyexcel实现
        try {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("系统日志.xlsx", "UTF-8"));
            EasyExcel.write(response.getOutputStream(), SysLogExportVO.class).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).sheet("系统日志").doWrite(result);
        } catch (IOException ignored) {
            throw new RuntimeException(ignored);
        }

        // hutool的excelutil实现
//        ExcelWriter writer = ExcelUtil.getWriter();
//        // 设置表头
//        writer.addHeaderAlias("id", "日志编号");
//        writer.addHeaderAlias("userAccount", "用户账号");
//        writer.addHeaderAlias("ipAddress", "IP地址");
//        writer.addHeaderAlias("requestUri", "请求地址");
//        writer.addHeaderAlias("requestMethod", "请求方式");
//        writer.addHeaderAlias("classMethod", "操作方法");
//        writer.addHeaderAlias("actionName", "动作名称");
//        writer.addHeaderAlias("status", "状态");
//        writer.addHeaderAlias("executionTime", "执行时间（秒）");
//        writer.addHeaderAlias("createdTime", "操作时间");
//        writer.setOnlyAlias(true);
//        writer.write(result, true);
//        writer.autoSizeColumnAll();
//
//        try {
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("系统日志.xls", "UTF-8"));
//            ServletOutputStream out = response.getOutputStream();
//            writer.flush(out);
//            // 关闭writer，释放内存
//            writer.close();
//            //关闭输出Servlet流
//            IoUtil.close(out);
//        } catch (IOException ignored) {
//            throw new RuntimeException(ignored);
//        }
    }
}
