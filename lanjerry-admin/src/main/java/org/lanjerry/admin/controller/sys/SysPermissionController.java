package org.lanjerry.admin.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysPermissionSaveOrUpdateDTO;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.vo.sys.SysPermissionInfoVO;
import org.lanjerry.admin.vo.sys.SysPermissionListVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.core.enums.sys.SysPermissionStatusEnum;
import org.lanjerry.common.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 系统权限表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@RestController
@RequestMapping("/sys/permission")
@Api(tags = "系统权限模块api", position = 30)
@ApiSupport(author = "38046851@qq.com")
public class SysPermissionController {

    @Autowired
    private SysPermissionService permissionService;

    @GetMapping("/list")
    @RequiresPermissions("sys:permission:list")
    @ApiOperation(value = "查询系统权限列表", position = 10)
    public ApiResult<List<SysPermissionListVO>> listPermissions() {
        return ApiResult.success(permissionService.listPermissions());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据权限编号查询权限信息", position = 20)
    public ApiResult<SysPermissionInfoVO> getPermission(@PathVariable("id") @ApiParam(value = "权限编号", required = true) Integer id) {
        return ApiResult.success(permissionService.getPermission(id));
    }

    @PostMapping
    @RequiresPermissions("sys:permission:save")
    @SysLog("新增系统权限")
    @ApiOperation(value = "新增系统权限", position = 30)
    public ApiResult savePermission(@RequestBody @Validated @ApiParam(value = "系统权限新增参数", required = true) SysPermissionSaveOrUpdateDTO dto) {
        this.permissionService.savePermission(dto);
        return ApiResult.success();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:permission:update")
    @SysLog("更新系统权限")
    @ApiOperation(value = "更新系统权限", position = 40)
    public ApiResult updatePermission(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "系统权限更新参数", required = true) SysPermissionSaveOrUpdateDTO dto) {
        this.permissionService.updatePermission(id, dto);
        return ApiResult.success();
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:permission:remove")
    @SysLog("删除系统权限")
    @ApiOperation(value = "删除系统权限", position = 50)
    public ApiResult removePermission(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id) {
        this.permissionService.removePermission(id);
        return ApiResult.success();
    }

    @PutMapping("/enable/{id}")
    @RequiresPermissions("sys:permission:enable")
    @SysLog("启用系统权限")
    @ApiOperation(value = "启用系统权限", position = 60)
    public ApiResult lock(@PathVariable("id") @ApiParam(value = "权限编号", required = true) Integer id) {
        permissionService.changePermissionStatus(id, SysPermissionStatusEnum.ENABLE);
        return ApiResult.success();
    }

    @PutMapping("/disable/{id}")
    @RequiresPermissions("sys:permission:disable")
    @SysLog("停用系统权限")
    @ApiOperation(value = "停用系统权限", position = 70)
    public ApiResult unlock(@PathVariable("id") @ApiParam(value = "权限编号", required = true) Integer id) {
        permissionService.changePermissionStatus(id, SysPermissionStatusEnum.DISABLE);
        return ApiResult.success();
    }

    @GetMapping("/tree")
    @ApiOperation(value = "查询树形结构系统权限列表", position = 80)
    public ApiResult<List<SysPermissionTreeVO>> treePermissions() {
        return ApiResult.success(permissionService.treePermissions());
    }
}

