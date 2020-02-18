package org.lanjerry.admin.vo.tool;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成数据库表查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolGenDbTableVO extends BaseEntity {

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

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-22 15:57:53", position = 30)
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "更新时间", example = "2018-11-22 15:57:53", position = 40)
    private LocalDateTime updateTime;
}
