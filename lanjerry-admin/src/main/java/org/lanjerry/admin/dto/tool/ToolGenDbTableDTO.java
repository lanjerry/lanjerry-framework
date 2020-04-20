package org.lanjerry.admin.dto.tool;

import org.lanjerry.common.core.bean.SplitPage;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成数据库表查询参数
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成数据库表查询参数")
public class ToolGenDbTableDTO extends SplitPage {

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称", example = "sys_user", position = 10)
    private String tableName;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述", example = "系统用户表", position = 20)
    private String tableComment;
}