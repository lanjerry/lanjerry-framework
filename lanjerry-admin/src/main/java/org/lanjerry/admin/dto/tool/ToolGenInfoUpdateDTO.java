package org.lanjerry.admin.dto.tool;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务基本信息更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成业务基本信息更新参数")
public class ToolGenInfoUpdateDTO extends BaseEntity {

    /**
     * 表名称
     */
    @NotBlank(message = "基本信息-表名称不能为空")
    @ApiModelProperty(value = "表名称", example = "sys_user", position = 10)
    private String tableName;

    /**
     * 表描述
     */
    @NotBlank(message = "基本信息-表描述不能为空")
    @ApiModelProperty(value = "表描述", example = "系统用户表", position = 20)
    private String tableComment;

    /**
     * 实体类
     */
    @NotBlank(message = "基本信息-实体类不能为空")
    @ApiModelProperty(value = "实体类", example = "SysUser", position = 30)
    private String className;

    /**
     * 生成功能作者
     */
    @NotBlank(message = "基本信息-作者不能为空")
    @ApiModelProperty(value = "作者", example = "lanjerry", position = 40)
    private String functionAuthor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注xxx", position = 50)
    private String remark;

    /**
     * 模板功能集
     */
    @NotNull(message = "生成信息-模板功能不能为空")
    @Size(min = 1, message = "请至少选择一个模板功能")
    @ApiModelProperty(value = "模板功能集", example = "[\"pageList\", \"add\", \"update\", \"delete\"]", position = 60)
    private List<String> tplFunctions;

    /**
     * 生成包路径
     */
    @NotBlank(message = "生成信息-生成包路径不能为空")
    @ApiModelProperty(value = "生成包路径", example = "org.lanjerry.admin", position = 70)
    private String packageName;

    /**
     * 生成模块名
     */
    @NotBlank(message = "生成信息-生成模块名不能为空")
    @ApiModelProperty(value = "生成模块名", example = "system", position = 80)
    private String moduleName;

    /**
     * 生成业务名
     */
    @NotBlank(message = "生成信息-生成业务名不能为空")
    @ApiModelProperty(value = "生成业务名", example = "user", position = 90)
    private String businessName;

    /**
     * 生成功能名
     */
    @NotBlank(message = "生成信息-生成功能名不能为空")
    @ApiModelProperty(value = "生成功能名", example = "用户", position = 100)
    private String functionName;
}