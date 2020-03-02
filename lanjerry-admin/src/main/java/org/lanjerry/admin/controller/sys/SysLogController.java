package org.lanjerry.admin.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysLogPageDTO;
import org.lanjerry.admin.service.sys.SysLogService;
import org.lanjerry.admin.vo.sys.SysLogInfoVO;
import org.lanjerry.admin.vo.sys.SysLogPageVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/page")
    @RequiresPermissions("sys:log:page")
    @ApiOperation(value = "分页查询系统日志列表", position = 10)
    public ApiResult<IPage<SysLogPageVO>> pageLogs(@ApiParam(value = "系统日志列表查询参数", required = true) SysLogPageDTO dto) {
        return ApiResult.success(logService.pageLogs(dto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据日志编号查询日志信息", position = 20)
    public ApiResult<SysLogInfoVO> getLog(@PathVariable("id") @ApiParam(value = "日志编号", required = true) Integer id) {
        return ApiResult.success(logService.getLog(id));
    }

    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:log:remove")
    @ApiOperation(value = "删除系统日志", position = 30)
    public ApiResult removeLogs(@PathVariable("ids") @ApiParam(value = "日志编号数组", required = true) Integer[] ids) {
        logService.removeLogs(ids);
        return ApiResult.success();
    }
}