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
 * 代码生成业务明细表
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tool_gen_detail")
public class ToolGenDetail extends BaseEntity {

    /**
     * 字段编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 表编号
     */
    private Integer tableId;

    /**
     * 列名称
     */
    private String columnName;

    /**
     * 列描述
     */
    private String columnComment;

    /**
     * 列类型
     */
    private String columnType;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * 是否主键 0.否 1.是
     */
    private Boolean pkFlag;

    /**
     * 是否自增 0.否 1.是
     */
    private Boolean incrementFlag;

    /**
     * 是否必填 0.否 1.是
     */
    private Boolean requiredFlag;

    /**
     * 是否唯一 0.否 1.是
     */
    private Boolean onlyFlag;

    /**
     * 是否列表字段 0.否 1.是
     */
    private Boolean listFlag;

    /**
     * 是否表单字段 0.否 1.是
     */
    private Boolean formFlag;

    /**
     * 表单显示类型
     */
    private String formHtmlType;

    /**
     * 是否查询字段 0.否 1.是
     */
    private Boolean queryFlag;

    /**
     * 查询方式
     */
    private String queryType;

    /**
     * 查询显示类型
     */
    private String queryHtmlType;

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
