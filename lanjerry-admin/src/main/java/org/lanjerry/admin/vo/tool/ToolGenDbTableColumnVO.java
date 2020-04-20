package org.lanjerry.admin.vo.tool;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成数据库表字段查询信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成数据库表字段查询信息")
public class ToolGenDbTableColumnVO extends BaseEntity {

    /**
     * 列名称
     */
    @ApiModelProperty(value = "列名称", example = "remark", position = 10)
    private String columnName;

    /**
     * 列描述
     */
    @ApiModelProperty(value = "列描述", example = "备注", position = 20)
    private String columnComment;

    /**
     * 列类型
     */
    @ApiModelProperty(value = "列类型", example = "varchar(255)", position = 30)
    private String columnType;

    /**
     * 是否主键 0.否 1.是
     */
    @ApiModelProperty(value = "是否主键 0.否 1.是", example = "1", position = 40)
    private Boolean pkFlag;

    /**
     * 是否自增 0.否 1.是
     */
    @ApiModelProperty(value = "是否自增 0.否 1.是", example = "1", position = 50)
    private Boolean incrementFlag;

    /**
     * 是否必填 0.否 1.是
     */
    @ApiModelProperty(value = "是否必填 0.否 1.是", example = "1", position = 60)
    private Boolean requiredFlag;
}