package org.lanjerry.common.core.enums.sys;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 权限类型枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysPermissionTypeEnum implements BaseEnum<Integer> {
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final Integer value;
    private final String text;

    SysPermissionTypeEnum(final Integer value, final String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getName() {
        return name();
    }
}
