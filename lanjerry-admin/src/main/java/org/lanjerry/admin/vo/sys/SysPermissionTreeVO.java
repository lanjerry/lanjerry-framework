package org.lanjerry.admin.vo.sys;

import java.util.List;

import org.lanjerry.common.core.bean.BaseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统权限树形结构查询信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel("系统权限树形结构查询信息")
public class SysPermissionTreeVO extends BaseEntity {

    /**
     * 权限编号
     */
    @ApiModelProperty(value = "权限编号", example = "1", position = 10)
    private Integer id;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称", example = "首页", position = 20)
    private String label;

    /**
     * 子权限列表
     */
    @ApiModelProperty(value = "子权限列表", position = 30)
    private List<SysPermissionTreeVO> children;
}
