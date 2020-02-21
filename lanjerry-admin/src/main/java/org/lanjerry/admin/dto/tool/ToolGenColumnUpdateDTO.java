package org.lanjerry.admin.dto.tool;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务字段更新DTO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    @ApiModelProperty(value = "是否必填 0.否 1.是", example = "是", position = 70)
    private Boolean requiredFlag;

    /**
     * 是否表单字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否表单字段 0.否 1.是", example = "是", position = 80)
    private Boolean formFlag;

    /**
     * 是否列表字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否列表字段 0.否 1.是", example = "是", position = 90)
    private Boolean listFlag;

    /**
     * 是否查询字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否查询字段 0.否 1.是", example = "是", position = 100)
    private Boolean queryFlag;

    /**
     * 查询方式
     */
    @ApiModelProperty(value = "查询方式", example = "eq", position = 110)
    private String queryType;

    /**
     * 显示类型
     */
    @ApiModelProperty(value = "显示类型", example = "input", position = 120)
    private String htmlType;
}