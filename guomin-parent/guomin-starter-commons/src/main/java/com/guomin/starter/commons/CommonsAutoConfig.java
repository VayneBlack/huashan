package com.guomin.starter.commons;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.Response;
import com.guomin.starter.commons.utils.RespUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Configuration
@ComponentScan
public class CommonsAutoConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,true);
        mapper.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,true);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 默认的时间序列化格式
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return mapper;
    }

    @ConditionalOnProperty(prefix = "cors",name = "enable",havingValue = "true")
    public class CorsConfig {
        @Bean
        public FilterRegistrationBean corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("*");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            source.registerCorsConfiguration("/**", config);
            FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
            bean.setOrder(0);
            return bean;
        }
    }


    @ConditionalOnProperty(prefix = "exception.handler",name = "enable",havingValue = "true")
    @RestControllerAdvice
    public class ExceptionHandle{
        /**
         * 业务异常处理
         */

        @ExceptionHandler(BussinessException.class)
        public Response bussinessException(BussinessException e){
            return RespUtil.error(e.getCode(),e.getMessage());
        }


        /**   参数解析失败   状态码:400   */
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public Response handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

            Throwable mostSpecificCause = e.getMostSpecificCause();

            String localizedMessage = mostSpecificCause.getLocalizedMessage();

            String field = StrUtil.subBetween(localizedMessage, "field", "(");

            return RespUtil.error(400,"请求体不需要"+field+"这些参数");
        }

        /**   校验失败  状态码:403   */
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Response handleConstraintViolationException(MethodArgumentNotValidException e) {
            List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
            String message = objectErrors.iterator().next().getDefaultMessage();
            return RespUtil.error(403,message);
        }

        @ExceptionHandler(Exception.class)
        public Response handleException(Exception e) {
            return  RespUtil.error(500,e.getMessage());
        }
    }

}
