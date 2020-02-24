package org.lanjerry.admin.vo.tool;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成基本信息VO
 * </p>
 *
 * @author lanjerry
 * @since 2020-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ToolGenInfoVO extends BaseEntity {

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
     * 实体类
     */
    @ApiModelProperty(value = "实体类", example = "SysUser", position = 30)
    private String className;

    /**
     * 生成功能作者
     */
    @ApiModelProperty(value = "作者", example = "lanjerry", position = 40)
    private String functionAuthor;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "备注xxx", position = 50)
    private String remark;

    /**
     * 模板功能集
     */
    @ApiModelProperty(value = "模板功能集", example = "[pageList, add, update, delete]", position = 60)
    private List<String> tplFunctions;

    /**
     * 生成包路径
     */
    @ApiModelProperty(value = "生成包路径", example = "org.lanjerry.admin", position = 70)
    private String packageName;

    /**
     * 生成模块名
     */
    @ApiModelProperty(value = "生成模块名", example = "system", position = 80)
    private String moduleName;

    /**
     * 生成业务名
     */
    @ApiModelProperty(value = "生成业务名", example = "user", position = 90)
    private String businessName;

    /**
     * 生成功能名
     */
    @ApiModelProperty(value = "生成功能名", example = "用户", position = 100)
    private String functionName;
}