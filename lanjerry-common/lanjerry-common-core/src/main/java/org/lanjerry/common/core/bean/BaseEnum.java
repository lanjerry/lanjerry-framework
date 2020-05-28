package org.lanjerry.common.core.bean;

import java.io.Serializable;

import org.lanjerry.common.core.jackson.serializer.CustomEnumSerializer;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * <p>
 * BaseEnum
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-20
 */
@JsonSerialize(using = CustomEnumSerializer.class)
public interface BaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 枚举数据库存储值
     */
    T getValue();

    /**
     * 展示文本
     */
    String getText();

    /**
     * 枚举名称
     */
    String getName();
}