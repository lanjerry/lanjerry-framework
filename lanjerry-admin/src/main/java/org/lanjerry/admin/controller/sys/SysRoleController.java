package org.lanjerry.admin.controller.sys;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.annotation.SysLog;
import org.lanjerry.admin.dto.sys.SysRolePageDTO;
import org.lanjerry.admin.dto.sys.SysRoleSaveOrUpdateDTO;
import org.lanjerry.admin.service.sys.SysRoleService;
import org.lanjerry.admin.vo.sys.SysRolePageVO;
import org.lanjerry.common.core.bean.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/page")
    @RequiresPermissions("sys:role:page")
    @ApiOperation(value = "分页获取系统角色列表", position = 10)
    public R<IPage<SysRolePageVO>> pageUsers(@RequestBody @ApiParam(value = "系统角色列表查询参数", required = true) SysRolePageDTO dto) {
        return R.ok(roleService.pageRoles(dto));
    }

    @PostMapping
    @RequiresPermissions("sys:role:save")
    @SysLog("新增系统角色")
    @ApiOperation(value = "新增系统角色", position = 20)
    public R saveRole(@RequestBody @Validated @ApiParam(value = "系统角色新增参数", required = true) SysRoleSaveOrUpdateDTO dto) {
        roleService.saveRole(dto);
        return R.ok();
    }

    @PutMapping("/{id}")
    @RequiresPermissions("sys:role:update")
    @SysLog("更新系统角色")
    @ApiOperation(value = "更新系统角色", position = 30)
    public R updateRole(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "系统角色更新参数", required = true) SysRoleSaveOrUpdateDTO dto) {
        roleService.updateRole(id, dto);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("sys:role:remove")
    @SysLog("删除系统角色")
    @ApiOperation(value = "删除系统角色", position = 40)
    public R removeRole(@PathVariable("id") @ApiParam(value = "id", required = true) Integer id) {
        roleService.removeRole(id);
        return R.ok();
    }
}

