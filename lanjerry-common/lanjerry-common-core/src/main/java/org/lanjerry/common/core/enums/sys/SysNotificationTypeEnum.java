package org.lanjerry.common.core.enums.sys;

import org.lanjerry.common.core.bean.BaseEnum;

/**
 * <p>
 * 系统消息通知类型枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysNotificationTypeEnum implements BaseEnum<Integer> {
    NUMBER(1, "数量"),
    CONTENT(2, "内容");

    private final Integer value;
    private final String text;

    SysNotificationTypeEnum(final Integer value, final String text) {
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