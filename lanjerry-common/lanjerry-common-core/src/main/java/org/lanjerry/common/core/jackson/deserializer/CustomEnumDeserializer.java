package org.lanjerry.common.core.jackson.deserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.lanjerry.common.core.bean.BaseEnum;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * <p>
 * 自定义枚举反序列化
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-23
 */
public class CustomEnumDeserializer extends JsonDeserializer<BaseEnum> {

    @Override
    public BaseEnum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        Class<BaseEnum> findPropertyType = (Class<BaseEnum>) BeanUtils.findPropertyType(jp.currentName(), jp.getCurrentValue().getClass());
        Optional<BaseEnum> any = Arrays.stream(findPropertyType.getEnumConstants()).filter(e -> e.getValue().equals(node.asInt())).findAny();
        return any.orElseGet(null);
    }
}