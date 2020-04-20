package org.lanjerry.admin.dto.tool;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务字段更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成业务字段更新参数")
public class ToolGenColumnUpdateDTO extends BaseEntity {

    /**
     * 字段编号
     */
    @NotNull(message = "字段编号不能为空")
    @ApiModelProperty(value = "字段编号", example = "1", position = 10)
    private Integer id;

    /**
     * 列描述
     */
    @NotBlank(message = "列描述不能为空")
    @ApiModelProperty(value = "列描述", example = "用户编号", position = 20)
    private String columnComment;

    /**
     * 是否必填 0.否 1.是
     */
    @ApiModelProperty(value = "是否必填 0.否 1.是", example = "是", position = 30)
    private Boolean requiredFlag;

    /**
     * 是否唯一 0.否 1.是
     */
    @ApiModelProperty(value = "是否唯一 0.否 1.是", example = "是", position = 40)
    private Boolean onlyFlag;

    /**
     * 是否列表字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否列表字段 0.否 1.是", example = "是", position = 50)
    private Boolean listFlag;

    /**
     * 是否表单字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否表单字段 0.否 1.是", example = "是", position = 60)
    private Boolean formFlag;

    /**
     * 表单显示类型
     */
    @ApiModelProperty(value = "表单显示类型", example = "input", position = 70)
    private String formHtmlType;

    /**
     * 是否查询字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否查询字段 0.否 1.是", example = "是", position = 80)
    private Boolean queryFlag;

    /**
     * 查询方式
     */
    @ApiModelProperty(value = "查询方式", example = "eq", position = 90)
    private String queryType;

    /**
     * 查询显示类型
     */
    @ApiModelProperty(value = "查询显示类型", example = "input", position = 100)
    private String queryHtmlType;

    /**
     * 是否导出字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否导出字段 0.否 1.是", example = "是", position = 110)
    private Boolean exportFlag;
}