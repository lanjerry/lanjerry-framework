package org.lanjerry.common.core.enums.sys;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>
 * 系统用户性别枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysUserSexEnum implements IEnum<Integer> {
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
    public Integer getValue() {
        return value;
    }

    //@JsonValue
    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }

}