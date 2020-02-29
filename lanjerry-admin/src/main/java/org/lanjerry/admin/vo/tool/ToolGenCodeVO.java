package org.lanjerry.admin.vo.tool;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolGenCodeVO extends BaseEntity {

    /**
     * 模板功能集
     */
    private List<String> tplFunctions;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;

    /**
     * 生成功能名
     */
    private String functionName;

    /**
     * 实体类
     */
    private String className;

    /**
     * 生成模块名
     */
    private String moduleName;

    /**
     * 生成业务名
     */
    private String businessName;

    /**
     * 生成包基本路径
     */
    private String basePackage;

    /**
     * 生成包路径
     */
    private String packageName;

    /**
     * 生成功能作者
     */
    private String functionAuthor;

    /**
     * 主键字段描述
     */
    private String pkComment;

    /**
     * 主键JAVA类型
     */
    private String pkJavaType;

    /**
     * 主键JAVA字段名
     */
    private String pkJavaField;

    /**
     * 表字段集
     */
    private List<ToolGenCodeColumnVO> columns;
}
