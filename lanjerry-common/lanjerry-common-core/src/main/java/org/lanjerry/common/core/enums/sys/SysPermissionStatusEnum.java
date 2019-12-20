package org.lanjerry.common.core.enums.sys;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 系统权限状态枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SysPermissionStatusEnum implements IEnum {
    SHOW(1, "正常"),
    HIDE(2, "隐藏");

    private final Integer value;
    private final String desc;

    SysPermissionStatusEnum(final Integer value, final String desc) {
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