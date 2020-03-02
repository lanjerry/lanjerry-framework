package org.lanjerry.admin.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.sys.SysRolePageDTO;
import org.lanjerry.admin.dto.sys.SysRoleSaveOrUpdateDTO;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.vo.sys.SysRoleInfoVO;
import org.lanjerry.admin.vo.sys.SysRolePageVO;
import org.lanjerry.admin.vo.sys.SysUserRoleVO;
import org.lanjerry.common.core.bean.ApiResult;
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
 * 系统角色表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/sys/role")
@Api(tags = "系统角色模块api")
public class SysRoleController {

    private final SysRoleService roleService;

    @GetMapping("/page")
    @RequiresPermissions("sys:role:page")
    @ApiOperation(value = "分页查询系统角色列表", position = 10)
    public ApiResult<IPage<SysRolePageVO>> pageRoles(@ApiParam(value = "系统角色列表查询参数", required = true) SysRolePageDTO dto) {
        return ApiResult.success(roleService.pageRoles(dto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据角色编号查询角色信息", position = 20)
    public ApiResult<SysRoleInfoVO> getRole(@PathVariable("id") @ApiParam(value = "角色编号", required = true) Integer id) {
        return ApiResult.success(roleService.getRole(id));
    }

    @PostMapping
    @RequiresPermissions("sys:role:save")
    @SysLog("新增系统角色")
    @ApiOperation(value = "新增系统角色", position = 30)
    public ApiResult saveRole(@RequestBody @Validated @ApiParam(value = "系统角色新增参数", required = true) SysRoleSaveOrUpdateDTO dto) {
        roleService.saveRole(dto);
        return ApiResult.success();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:role:update")
    @SysLog("更新系统角色")
    @ApiOperation(value = "更新系统角色", position = 40)
    public ApiResult updateRole(@PathVariable("id") @ApiParam(value = "角色编号", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "系统角色更新参数", required = true) SysRoleSaveOrUpdateDTO dto) {
        roleService.updateRole(id, dto);
        return ApiResult.success();
    }

    @DeleteMapping("/{ids}")
    @RequiresPermissions("sys:role:remove")
    @SysLog("删除系统角色")
    @ApiOperation(value = "删除系统角色", position = 50)
    public ApiResult removeRoles(@PathVariable("ids") @ApiParam(value = "角色编号数组", required = true) Integer[] ids) {
        roleService.removeRoles(ids);
        return ApiResult.success();
    }

    @GetMapping("/permissionIds/{id}")
    @ApiOperation(value = "根据角色编号查询角色的权限编号集", position = 60)
    public ApiResult<List<Integer>> getRolePermissionIds(@PathVariable("id") @ApiParam(value = "角色编号", required = true) Integer id) {
        return ApiResult.success(roleService.getRolePermissionIds(id));
    }

    @GetMapping("/list")
    @ApiOperation(value = "查询系统角色列表", position = 70)
    public ApiResult<List<SysUserRoleVO>> listRoles() {
        return ApiResult.success(roleService.listRoles());
    }
}