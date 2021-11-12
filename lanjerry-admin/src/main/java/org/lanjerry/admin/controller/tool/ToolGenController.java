package org.lanjerry.admin.controller.tool;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lanjerry.admin.dto.tool.ToolGenDbTableDTO;
import org.lanjerry.admin.dto.tool.ToolGenPageDTO;
import org.lanjerry.admin.dto.tool.ToolGenUpdateDTO;
import org.lanjerry.admin.service.tool.ToolGenService;
import org.lanjerry.admin.vo.tool.ToolGenDbTableVO;
import org.lanjerry.admin.vo.tool.ToolGenPageVO;
import org.lanjerry.admin.vo.tool.ToolGenPreviewVO;
import org.lanjerry.admin.vo.tool.ToolGenResultVO;
import org.lanjerry.common.core.bean.ApiResult;
import org.lanjerry.common.log.annotation.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 代码生成业务表 前端控制器
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@RestController
@RequestMapping("/tool/gen")
@Api(tags = "代码生成模块api", position = 50)
@ApiSupport(author = "38046851@qq.com")
public class ToolGenController {

    @Autowired
    private ToolGenService genService;

    @GetMapping("/page")
    @RequiresPermissions("tool:gen:page")
    @ApiOperation(value = "分页查询代码生成业务列表", position = 10)
    public ApiResult<IPage<ToolGenPageVO>> pageGens(@ApiParam(value = "代码生成业务查询参数", required = true) ToolGenPageDTO dto) {
        return ApiResult.success(genService.pageGens(dto));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据表编号查询代码生成业务信息", position = 20)
    public ApiResult<ToolGenResultVO> getGen(@PathVariable("id") @ApiParam(value = "表编号", required = true) Integer id) {
        return ApiResult.success(genService.getGen(id));
    }

    @PutMapping("/{id}")
    @RequiresPermissions("tool:gen:update")
    @SysLog("更新代码生成业务")
    @ApiOperation(value = "更新代码生成业务", position = 30)
    public ApiResult updateGen(@PathVariable("id") @ApiParam(value = "表编号", required = true) Integer id, @RequestBody @Validated @ApiParam(value = "代码生成业务更新参数", required = true) ToolGenUpdateDTO dto) {
        genService.updateGen(id, dto);
        return ApiResult.success();
    }

    @DeleteMapping("/{id}")
    @RequiresPermissions("tool:gen:remove")
    @SysLog("删除代码生成业务")
    @ApiOperation(value = "删除代码生成业务", position = 40)
    public ApiResult removeGens(@PathVariable("id") @ApiParam(value = "表编号数组", required = true) Integer[] ids) {
        genService.removeGens(ids);
        return ApiResult.success();
    }

    @GetMapping("/pageDbTables")
    @ApiOperation(value = "分页查询数据库表", position = 50)
    public ApiResult<IPage<ToolGenDbTableVO>> pageDbTables(@ApiParam(value = "数据库表查询参数", required = true) ToolGenDbTableDTO dto) {
        return ApiResult.success(genService.pageDbTables(dto));
    }

    @PostMapping("/importDbTables/{tables}")
    @ApiOperation(value = "导入数据库表", position = 60)
    public ApiResult importDbTables(@PathVariable("tables") @ApiParam(value = "数据库表数组", required = true) String[] tableNames) {
        genService.importDbTables(tableNames);
        return ApiResult.success();
    }

    @GetMapping("/preview/{id}")
    @RequiresPermissions("tool:gen:preview")
    @ApiOperation(value = "预览代码生成", position = 70)
    public ApiResult<List<ToolGenPreviewVO>> preview(@PathVariable("id") @ApiParam(value = "表编号", required = true) Integer id) {
        return ApiResult.success(genService.preview(id));
    }

    @GetMapping("/code")
    @RequiresPermissions("tool:gen:code")
    @ApiOperation(value = "代码生成", position = 80)
    public void code(HttpServletResponse response, Integer[] ids) throws IOException {
        byte[] data = genService.code(ids);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"lanjerry.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}