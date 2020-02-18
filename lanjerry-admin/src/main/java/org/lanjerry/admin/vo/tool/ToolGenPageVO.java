package org.lanjerry.admin.vo.tool;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务分页查询VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolGenPageVO extends BaseEntity {

    /**
     * 表编号
     */
    @ApiModelProperty(value = "表编号", example = "1", position = 10)
    private Integer id;

    /**
     * 表名称
     */
    @ApiModelProperty(value = "表名称", example = "sys_user", position = 20)
    private String tableName;

    /**
     * 表描述
     */
    @ApiModelProperty(value = "表描述", example = "系统用户表", position = 30)
    private String tableComment;

    /**
     * 实体类
     */
    @ApiModelProperty(value = "实体类", example = "SysUser", position = 40)
    private String className;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "管理员", position = 50)
    private String creatorName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2018-11-23", position = 60)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2018-11-23", position = 70)
    private LocalDateTime updatedTime;
}
