package org.lanjerry.admin.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysNotificationPageDTO;
import org.lanjerry.admin.dto.sys.SysNotificationSaveDTO;
import org.lanjerry.admin.service.sys.SysNotificationService;
import org.lanjerry.admin.vo.sys.SysNotificationPageVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 系统消息前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/sys/notification")
@Api(tags = "系统消息模块api")
public class SysNotificationController {

    @Autowired
    private SysNotificationService notificationService;

    @GetMapping("/page")
    @RequiresPermissions("sys:notification:page")
    @ApiOperation(value = "分页查询系统消息列表", position = 10)
    public ApiResult<IPage<SysNotificationPageVO>> pageNotifications(@ApiParam(value = "系统消息查询参数", required = true) SysNotificationPageDTO dto) {
        return ApiResult.success(notificationService.pageNotifications(dto));
    }

    @PostMapping
    @RequiresPermissions("sys:notification:save")
    @SysLog("新增系统消息")
    @ApiOperation(value = "新增系统消息", position = 20)
    public ApiResult saveNotification(@RequestBody @Validated @ApiParam(value = "系统消息新增参数", required = true) SysNotificationSaveDTO dto) {
        notificationService.saveNotification(dto);
        return ApiResult.success();
    }

    @PostMapping("/read/{ids}")
    @RequiresPermissions("sys:notification:read")
    @ApiOperation(value = "已读系统消息", position = 30)
    public ApiResult readNotifications(@PathVariable("ids") @ApiParam(value = "编号数组", required = true) Integer[] ids) {
        notificationService.readNotifications(ids);
        return ApiResult.success();
    }
}