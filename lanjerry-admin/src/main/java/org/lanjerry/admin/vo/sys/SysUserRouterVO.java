package org.lanjerry.admin.vo.sys;

import org.lanjerry.common.core.bean.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户路由VO
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRouterVO extends BaseEntity {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 是否隐藏路由，当设置 true 的时候该路由不会再侧边栏出现
     */
    private String hidden;

    /**
     * 重定向地址，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
     */
    private String redirect;

    /**
     * 组件地址
     */
    private String component;

    /**
     * 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
     */
    private Boolean alwaysShow;

    /**
     * 路由显示信息
     */
    private SysUserRouterMetaVO meta;
}