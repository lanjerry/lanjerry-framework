package org.lanjerry.common.core.entity.tool;

import java.time.LocalDateTime;

import org.lanjerry.common.core.bean.BaseEntity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成业务表
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tool_gen")
public class ToolGen extends BaseEntity {

    /**
     * 表编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 实体类
     */
    private String className;

    /**
     * 模板功能
     */
    private String tplFunction;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成业务名
     */
    private String businessName;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 生成功能作者
     */
    private String functionAuthor;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer creatorId;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creatorName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
}
