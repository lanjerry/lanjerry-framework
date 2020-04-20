package org.lanjerry.admin.vo.tool;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成查询信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成查询信息")
public class ToolGenResultVO extends BaseEntity {

    /**
     * 代码生成基本信息
     */
    @ApiModelProperty(value = "代码生成基本信息", position = 10)
    private ToolGenInfoVO info;

    /**
     * 代码生成字段列表信息
     */
    @ApiModelProperty(value = "代码生成字段列表信息",  position = 20)
    private List<ToolGenColumnVO> columns;
}
