package org.lanjerry.common.core.enums.sys;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 系统用户性别枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysUserSexEnum implements BaseEnum<Integer> {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    private final Integer value;
    private final String text;

    SysUserSexEnum(final Integer value, final String text) {
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