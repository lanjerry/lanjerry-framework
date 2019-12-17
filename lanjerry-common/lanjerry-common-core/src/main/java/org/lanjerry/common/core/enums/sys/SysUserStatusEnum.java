package org.lanjerry.common.core.enums.sys;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * <p>
 * 系统用户状态枚举
 * </p>
 *
 * @author lanjerry
 * @since 2019-09-03
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SysUserStatusEnum implements IEnum {
    NORMAL(1, "正常"),
    LOCKING(2, "冻结");

    private final Integer value;
    private final String desc;

    SysUserStatusEnum(final Integer value, final String desc) {
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