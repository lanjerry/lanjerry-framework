package org.lanjerry.common.core.enums.sys;

import java.io.Serializable;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 系统用户状态枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysUserStatusEnum implements BaseEnum {
    NORMAL(1, "正常"),
    LOCKING(2, "冻结");

    private final Integer value;
    private final String text;

    SysUserStatusEnum(final Integer value, final String text) {
        this.value = value;
        this.text = text;
    }


    @Override
    public Serializable getValue() {
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