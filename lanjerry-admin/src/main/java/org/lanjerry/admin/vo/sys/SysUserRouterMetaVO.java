package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户路由显示信息VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRouterMetaVO extends BaseEntity {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;
}
