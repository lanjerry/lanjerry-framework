package org.lanjerry.common.core.enums;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 权限类型枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PermissionTypeEnum implements IEnum {
    MENU(1, "菜单"),
    AUTH(2, "功能");

    private final Integer value;
    private final String desc;

    PermissionTypeEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getEnumName() {
        return name();
    }
}
