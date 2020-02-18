package org.lanjerry.admin.controller.tool;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.service.tool.ToolGenService;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.log.annotation.SysLog;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

/**
 * <p>
 * 代码生成业务表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/tool/gen")
@Api(tags = "代码生成模块api")
public class ToolGenController {

    private final ToolGenService genService;

    @GetMapping("/page")
    @RequiresPermissions("tool:gen:page")
    @ApiOperation(value = "分页查询代码生成业务列表", position = 10)
    public ApiResult<IPage<ToolGenPageVO>> pageGens(@ApiParam(value = "代码生成业务列表查询参数", required = true) ToolGenPageDTO dto) {
        return ApiResult.success(genService.pageGens(dto));
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("tool:gen:remove")
    @SysLog("删除代码生成业务")
    @ApiOperation(value = "删除代码生成业务", position = 20)
    public ApiResult removeGens(@PathVariable("id") @ApiParam(value = "表编号", required = true) Integer[] ids) {
        genService.removeGens(ids);
        return ApiResult.success();
    }

    @GetMapping("/pageDbTables")
    @ApiOperation(value = "分页查询数据库表", position = 30)
    public ApiResult<IPage<ToolGenDbTableVO>> pageDbTables(@ApiParam(value = "数据库表查询参数", required = true) ToolGenDbTableDTO dto) {
        return ApiResult.success(genService.pageDbTables(dto));
    }

    @PostMapping("/importDbTables/{tables}")
    @ApiOperation(value = "导入数据库表", position = 40)
    public ApiResult importDbTables(@PathVariable("tables") @ApiParam(value = "数据库表数组", required = true) String[] tableNames) {
        genService.importDbTables(tableNames);
        return ApiResult.success();
    }
}