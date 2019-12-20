package org.lanjerry.common.core.enums.sys;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * <p>
 * 权限类型枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysPermissionTypeEnum implements IEnum<Integer> {
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private final Integer value;
    private final String desc;

    SysPermissionTypeEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }
}
