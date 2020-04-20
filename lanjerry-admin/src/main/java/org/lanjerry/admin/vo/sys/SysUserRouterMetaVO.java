package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户路由显示信息
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-10
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户路由显示信息")
public class SysUserRouterMetaVO extends BaseEntity {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    @ApiModelProperty(value = "路由标题", example = "系统管理", position = 10)
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    @ApiModelProperty(value = "路由图标", example = "system", position = 20)
    private String icon;
}
