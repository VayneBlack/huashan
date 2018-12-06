package com.guomin.starter.commons.utils;


import com.guomin.starter.commons.exceptions.BussinessException;
import com.guomin.starter.commons.model.Response;

public class RespUtil {

    /**
     * 返回成功，传入返回体具体出參
     * @param object
     * @return
     */
	public static Response success(Object object){
		Response result = new Response();
        result.setStatus(200);
        result.setMsg("success");
        result.setData(object);
        return result;
    }

    /**
     * 提供给部分不需要入参的接口
     * @return
     */
    public static Response success(){
        return success(null);
    }

    public static Response error(Integer code, String msg){
		Response result = new Response();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Response error(BussinessException exceptionEnum){
		Response result = new Response();
        result.setStatus(exceptionEnum.getCode());
        result.setMsg(exceptionEnum.getMessage());
        result.setData("");
        return result;
    }

    public static Response error(Integer code, String msg, Object object){
		Response result = new Response();
        result.setStatus(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}