package org.lanjerry.common.core.enums.sys;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 系统用户性别枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SysUserSexEnum implements IEnum {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    private final Integer value;
    private final String desc;

    SysUserSexEnum(final Integer value, final String desc) {
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