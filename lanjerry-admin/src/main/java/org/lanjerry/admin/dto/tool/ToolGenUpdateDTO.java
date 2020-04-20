package org.lanjerry.admin.dto.tool;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务更新参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成业务更新参数")
public class ToolGenUpdateDTO extends BaseEntity {

    /**
     * 代码生成基本信息
     */
    @NotNull(message = "基本信息不能为空")
    @ApiModelProperty(value = "代码生成基本信息", position = 10)
    private @Valid ToolGenInfoUpdateDTO info;

    /**
     * 代码生成字段列表信息
     */
    @NotNull(message = "字段列表信息不能为空")
    @ApiModelProperty(value = "代码生成字段列表信息",  position = 20)
    private List<@Valid ToolGenColumnUpdateDTO> columns;
}