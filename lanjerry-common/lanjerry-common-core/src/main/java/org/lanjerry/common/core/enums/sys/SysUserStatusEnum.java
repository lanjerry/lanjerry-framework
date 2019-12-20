package org.lanjerry.common.core.enums.sys;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * <p>
 * 系统用户状态枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
public enum SysUserStatusEnum implements IEnum<Integer> {
    NORMAL(1, "正常"),
    LOCKING(2, "冻结");

    private final Integer value;
    private final String desc;

    SysUserStatusEnum(final Integer value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return getDesc();
    }
}