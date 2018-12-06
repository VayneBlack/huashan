package com.guomin.starter.commons.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018-06-10  01:44:24
 * @Description 类描述: BigDecimal序列化为字符
 */
public class BigDecimalJsonSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String result = value == null ? null : value.stripTrailingZeros().toPlainString();
        if (result !=null ){
            gen.writeString(result);
        }
    }

}