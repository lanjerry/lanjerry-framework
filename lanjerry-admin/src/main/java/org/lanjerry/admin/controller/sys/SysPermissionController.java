package org.lanjerry.admin.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.annotation.SysLog;
import org.lanjerry.admin.dto.sys.SysPermissionSaveDTO;
import org.lanjerry.admin.dto.sys.SysPermissionUpdateDTO;
import org.lanjerry.admin.service.sys.SysPermissionService;
import org.lanjerry.admin.vo.sys.SysPermissionFindVO;
import org.lanjerry.admin.vo.sys.SysPermissionTreeVO;
import org.lanjerry.common.core.bean.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@AllArgsConstructor
@RequestMapping("/sys/permission")
@Api(tags = "系统权限模块api")
public class SysPermissionController {

    private final SysPermissionService permissionService;

    @GetMapping("/list")
    @RequiresPermissions("sys:permission:list")
    @ApiOperation(value = "获取系统权限列表", position = 10)
    public R<List<SysPermissionFindVO>> listPermissions() {
        return R.ok(permissionService.listPermissions());
    }

    @GetMapping("/tree")
    @ApiOperation(value = "获取树形结构系统权限列表", position = 20)
    public R<List<SysPermissionTreeVO>> treePermissions() {
        return R.ok(permissionService.treePermissions());
    }

    @PostMapping
    @RequiresPermissions("sys:permission:save")
    @SysLog("新增系统权限")
    @ApiOperation(value = "新增系统权限", position = 30)
    public R savePermission(@RequestBody @Validated @ApiParam(value = "系统权限新增参数", required = true) SysPermissionSaveDTO dto) {
        this.permissionService.savePermission(dto);
        return R.ok();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:permission:update")
    @SysLog("更新系统权限")
    @ApiOperation(value = "更新系统权限", position = 40)
    public R updatePermission(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "系统权限更新参数", required = true) SysPermissionUpdateDTO dto) {
        this.permissionService.updatePermission(id, dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:permission:remove")
    @SysLog("删除系统权限")
    @ApiOperation(value = "删除系统权限", position = 50)
    public R removePermission(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id) {
        this.permissionService.removePermission(id);
        return R.ok();
    }
}

