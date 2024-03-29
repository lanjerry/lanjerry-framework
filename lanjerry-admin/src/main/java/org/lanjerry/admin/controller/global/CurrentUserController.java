package org.lanjerry.admin.controller.global;

import java.util.List;

import org.lanjerry.admin.dto.global.CurrentUserLoginDTO;
import org.lanjerry.admin.dto.global.CurrentUserPasswordUpdateDTO;
import org.lanjerry.admin.dto.global.CurrentUserProfileUpdateDTO;
import org.lanjerry.admin.service.global.CurrentUserService;
import org.lanjerry.admin.vo.global.CurrentUserInfoVO;
import org.lanjerry.admin.vo.global.CurrentUserProfileVO;
import org.lanjerry.admin.vo.global.CurrentUserRouterVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 当前用户 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-25
 */
@RestController
@RequestMapping("/sys/user")
@Api(tags = "当前用户模块api", position = 2)
@ApiSupport(author = "38046851@qq.com")
public class CurrentUserController {

    @Autowired
    private CurrentUserService currentUserService;

    @PostMapping("/login")
    @ApiOperation(value = "当前用户登录", position = 10)
    public ApiResult login(@RequestBody @Validated @ApiParam(value = "当前用户登录参数", required = true) CurrentUserLoginDTO dto) {
        return ApiResult.success(currentUserService.login(dto));
    }

    @GetMapping("/info")
    @ApiOperation(value = "查询当前登录用户信息", position = 20)
    public ApiResult<CurrentUserInfoVO> getCurrentUserInfo() {
        return ApiResult.success(currentUserService.getCurrentUserInfo());
    }

    @GetMapping("/router")
    @ApiOperation(value = "查询当前登录用户的路由信息", position = 30)
    public ApiResult<List<CurrentUserRouterVO>> router() {
        return ApiResult.success(currentUserService.router());
    }

    @GetMapping("/profile")
    @ApiOperation(value = "查询当前登录用户基本资料", position = 40)
    public ApiResult<CurrentUserProfileVO> getCurrentUserProfile() {
        return ApiResult.success(currentUserService.getCurrentUserProfile());
    }

    @PutMapping("/profile")
    @ApiOperation(value = "更新当前登录用户基本资料", position = 50)
    public ApiResult updateCurrentUserProfile(@RequestBody @Validated @ApiParam(value = "当前登录用户基本资料更新参数", required = true) CurrentUserProfileUpdateDTO dto) {
        currentUserService.updateCurrentUserProfile(dto);
        return ApiResult.success();
    }

    @PutMapping("/profile/updatePassword")
    @ApiOperation(value = "更新当前登录用户密码", position = 60)
    public ApiResult updateCurrentUserPassword(@RequestBody @Validated @ApiParam(value = "当前登录用户密码更新参数", required = true) CurrentUserPasswordUpdateDTO dto) {
        currentUserService.updateCurrentUserPassword(dto);
        return ApiResult.success();
    }

    @PostMapping("/profile/avatar")
    @ApiOperation(value = "更新用户头像", position = 70)
    public ApiResult<String> updateCurrentUserAvatar(@RequestParam("avatarfile") MultipartFile file) {
        return ApiResult.success(currentUserService.updateCurrentUserAvatar(file));
    }

    @GetMapping("/notification/count")
    @ApiOperation(value = "查询当前登录用户消息通知个数", position = 80)
    public ApiResult<Integer> getCurrentUserNotificationCount() {
        return ApiResult.success(currentUserService.getCurrentUserNotificationCount());
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出登录", position = 90)
    public ApiResult logout() {
        currentUserService.logout();
        return ApiResult.success();
    }
}