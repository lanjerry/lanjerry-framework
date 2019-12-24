package org.lanjerry.common.core.jackson.serializer;

import java.io.IOException;

import org.lanjerry.common.core.bean.BaseEnum;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * <p>
 * 自定义枚举序列化
 * </p>
 *
 * @author lanjerry
 * @since 2019-12-20
 */
public class CustomEnumSerializer extends JsonSerializer {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        BaseEnum baseEnum = (BaseEnum) value;
        gen.writeStartObject();
        gen.writeNumberField("value", (Integer) baseEnum.getValue());
        gen.writeStringField("text", baseEnum.getText());
        gen.writeStringField("name", baseEnum.getName());
        gen.writeEndObject();
    }
}
