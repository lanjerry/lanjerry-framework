package org.lanjerry.admin.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysUserLoginDTO;
import org.lanjerry.admin.dto.sys.SysUserPageDTO;
import org.lanjerry.admin.dto.sys.SysUserResetPasswordDTO;
import org.lanjerry.admin.dto.sys.SysUserSaveDTO;
import org.lanjerry.admin.dto.sys.SysUserUpdateDTO;
import org.lanjerry.admin.service.sys.SysUserService;
import org.lanjerry.admin.vo.sys.SysUserCurrentVO;
import org.lanjerry.admin.vo.sys.SysUserInfoVO;
import org.lanjerry.admin.vo.sys.SysUserPageVO;
import org.lanjerry.admin.vo.sys.SysUserRouterVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.core.enums.sys.SysUserStatusEnum;
import org.lanjerry.common.log.annotation.SysLog;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
 * 系统用户表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/user")
@Api(tags = "系统用户模块api")
public class SysUserController {

    private final SysUserService userService;

    @GetMapping("/page")
    @RequiresPermissions("sys:user:page")
    @ApiOperation(value = "分页查询系统用户列表", position = 10)
    public ApiResult<IPage<SysUserPageVO>> pageUsers(@ApiParam(value = "系统用户列表查询参数", required = true) SysUserPageDTO dto) {
        return ApiResult.success(userService.pageUsers(dto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户编号查询用户信息", position = 20)
    public ApiResult<SysUserInfoVO> getUser(@PathVariable("id") @ApiParam(value = "用户编号", required = true) Integer id) {
        return ApiResult.success(userService.getUser(id));
    }

    @PostMapping
    @RequiresPermissions("sys:user:save")
    @SysLog("新增系统用户")
    @ApiOperation(value = "新增系统用户", position = 30)
    public ApiResult saveUser(@RequestBody @Validated @ApiParam(value = "系统用户新增参数", required = true) SysUserSaveDTO dto) {
        userService.saveUser(dto);
        return ApiResult.success();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:user:update")
    @SysLog("更新系统用户")
    @ApiOperation(value = "更新系统用户", position = 40)
    public ApiResult updateUser(@PathVariable("id") @ApiParam(value = "用户编号", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "系统用户更新参数", required = true) SysUserUpdateDTO dto) {
        userService.updateUser(id, dto);
        return ApiResult.success();
    }

    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:user:remove")
    @SysLog("删除系统用户")
    @ApiOperation(value = "删除系统用户", position = 50)
    public ApiResult removeUsers(@PathVariable("ids") @ApiParam(value = "用户编号数组", required = true) Integer[] ids) {
        userService.removeUsers(ids);
        return ApiResult.success();
    }

    @PutMapping("/lock/{id}")
    @RequiresPermissions("sys:user:lock")
    @SysLog("锁定系统用户")
    @ApiOperation(value = "锁定系统用户", position = 60)
    public ApiResult lock(@PathVariable("id") @ApiParam(value = "用户编号", required = true) Integer id) {
        userService.changeUserStatus(id, SysUserStatusEnum.LOCKING);
        return ApiResult.success();
    }

    @PutMapping("/unlock/{id}")
    @RequiresPermissions("sys:user:unlock")
    @SysLog("解锁系统用户")
    @ApiOperation(value = "解锁系统用户", position = 70)
    public ApiResult unlock(@PathVariable("id") @ApiParam(value = "用户编号", required = true) Integer id) {
        userService.changeUserStatus(id, SysUserStatusEnum.NORMAL);
        return ApiResult.success();
    }

    @PutMapping("/resetPassword")
    @RequiresPermissions("sys:user:resetPassword")
    @SysLog("重置系统用户密码")
    @ApiOperation(value = "重置系统用户密码", position = 80)
    public ApiResult resetUserPwd(@RequestBody @Validated @ApiParam(value = "系统用户重置密码参数", required = true) SysUserResetPasswordDTO dto) {
        userService.resetUserPwd(dto);
        return ApiResult.success();
    }

    @PostMapping("/login")
    @ApiOperation(value = "系统用户登录", position = 90)
    public ApiResult login(@RequestBody @Validated @ApiParam(value = "系统用户登录参数", required = true) SysUserLoginDTO dto) {
        return ApiResult.success(userService.login(dto));
    }

    @GetMapping("/info")
    @ApiOperation(value = "查询当前登录用户信息", position = 100)
    public ApiResult<SysUserCurrentVO> getCurrentUserinfo() {
        return ApiResult.success(userService.getCurrentUserinfo());
    }

    @GetMapping("/router")
    @ApiOperation(value = "查询当前登录用户的路由信息", position = 110)
    public ApiResult<List<SysUserRouterVO>> router() {
        return ApiResult.success(userService.router());
    }
}