package org.lanjerry.admin.vo.tool;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成字段信息
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("代码生成字段信息")
public class ToolGenCodeColumnVO extends BaseEntity {

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
     * 列长度
     */
    private String columnLength;

    /**
     * JAVA类型
     */
    private String javaType;

    /**
     * JAVA字段名
     */
    private String javaField;

    /**
     * JAVA字段名首字母大写
     */
    private String upperFirstJavaField;

    /**
     * 字段例子
     */
    private String columnExample;

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
     * 表单字段初始值
     */
    private String formInitValue;

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
     * 查询字段初始值
     */
    private String queryInitValue;

    /**
     * 是否导出字段 0.否 1.是
     */
    private Boolean exportFlag;
}
