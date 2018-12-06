package com.guomin.starter.commons.utils;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;

/**
 * @Auther 创建者: Tc李
 * @Date 创建时间: 2018-05-24  11:34:10
 * @Description 类描述:  对象互拷工具
 */

public class DozerUtil {

	/**
	 * @method 方法名: copy
	 * @Decription 方法描述:   拷贝时，忽略空值，null值，不覆盖多余属性值
	 *
	 * @params 传入参数:[source, target]
	 * @return 返回值类型:void
	 * @throws
	 */

	public static void copy(Object source,Object target){
    	Mapper mapper = DozerBeanMapperBuilder.create()
    			.withMappingBuilder(new BeanMappingBuilder() {
    				protected void configure() {
	    					mapping(source.getClass(), target.getClass(), TypeMappingOptions.oneWay(),TypeMappingOptions.mapNull(false),TypeMappingOptions.mapEmptyString(false));
    					}
    					}
    			)
    			.build();
        mapper.map(source,target);
    }
}