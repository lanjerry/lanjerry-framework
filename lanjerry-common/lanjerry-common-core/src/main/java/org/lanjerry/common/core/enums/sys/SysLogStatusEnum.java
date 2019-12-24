package org.lanjerry.common.core.enums.sys;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 系统用户状态枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysLogStatusEnum implements BaseEnum<Integer> {
    SUCCESS(1, "成功"),
    FAIL(2, "失败");

    private final Integer value;
    private final String text;

    SysLogStatusEnum(final Integer value, final String text) {
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