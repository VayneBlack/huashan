package com.guomin.starter.commons.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class BigDecimalJsonDeserializer extends JsonDeserializer<BigDecimal> {
    private static final Logger logger = LoggerFactory.getLogger(BigDecimalJsonDeserializer.class);
  
    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
        String value = jsonParser.getText();
        try {
            return value == null ? null : new BigDecimal(value);
        } catch (NumberFormatException e) {
            logger.error("解析BigDecimal错误", e);
            return null;
        }
    }


}