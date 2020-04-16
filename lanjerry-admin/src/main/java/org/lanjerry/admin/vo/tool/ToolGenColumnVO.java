package org.lanjerry.admin.vo.tool;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成字段列表信息VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolGenColumnVO extends BaseEntity {

    /**
     * 字段编号
     */
    @ApiModelProperty(value = "字段编号", example = "1", position = 10)
    private Integer id;

    /**
     * 列名称
     */
    @ApiModelProperty(value = "列名称", example = "id", position = 20)
    private String columnName;

    /**
     * 列描述
     */
    @ApiModelProperty(value = "列描述", example = "用户编号", position = 30)
    private String columnComment;

    /**
     * 列类型
     */
    @ApiModelProperty(value = "列类型", example = "int(11)", position = 40)
    private String columnType;

    /**
     * JAVA类型
     */
    @ApiModelProperty(value = "JAVA类型", example = "Integer", position = 50)
    private String javaType;

    /**
     * JAVA字段名
     */
    @ApiModelProperty(value = "JAVA字段名", example = "id", position = 60)
    private String javaField;

    /**
     * 是否必填 0.否 1.是
     */
    @ApiModelProperty(value = "是否必填 0.否 1.是", example = "是", position = 70)
    private Boolean requiredFlag;

    /**
     * 是否唯一 0.否 1.是
     */
    @ApiModelProperty(value = "是否唯一 0.否 1.是", example = "是", position = 80)
    private Boolean onlyFlag;

    /**
     * 是否列表字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否列表字段 0.否 1.是", example = "是", position = 90)
    private Boolean listFlag;

    /**
     * 是否表单字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否表单字段 0.否 1.是", example = "是", position = 100)
    private Boolean formFlag;

    /**
     * 表单显示类型
     */
    @ApiModelProperty(value = "表单显示类型", example = "input", position = 110)
    private String formHtmlType;

    /**
     * 是否查询字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否查询字段 0.否 1.是", example = "是", position = 120)
    private Boolean queryFlag;

    /**
     * 查询方式
     */
    @ApiModelProperty(value = "查询方式", example = "eq", position = 130)
    private String queryType;

    /**
     * 查询显示类型
     */
    @ApiModelProperty(value = "查询显示类型", example = "input", position = 140)
    private String queryHtmlType;

    /**
     * 是否导出字段 0.否 1.是
     */
    @ApiModelProperty(value = "是否导出字段 0.否 1.是", example = "是", position = 150)
    private Boolean exportFlag;
}
