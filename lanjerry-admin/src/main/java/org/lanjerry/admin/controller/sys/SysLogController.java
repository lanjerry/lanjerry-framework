package org.lanjerry.admin.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysLogPageDTO;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.admin.vo.sys.SysLogPageVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 系统日志表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/log")
@Api(tags = "系统日志模块api")
public class SysLogController {

    private final SysLogService logService;

    @PostMapping("/page")
    @RequiresPermissions("sys:log:page")
    @ApiOperation(value = "分页获取系统日志列表", position = 10)
    public ApiResult<IPage<SysLogPageVO>> pageUsers(@RequestBody @ApiParam(value = "系统日志列表查询参数", required = true) SysLogPageDTO dto) {
        return ApiResult.success(logService.pageLogs(dto));
    }
}