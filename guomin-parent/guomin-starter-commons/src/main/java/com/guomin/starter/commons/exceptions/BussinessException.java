package com.guomin.starter.commons.exceptions;

public class BussinessException extends Exception{

		private static final long serialVersionUID = 1L;
		private Integer code;


        /**
         * 自定义错误信息
         * @param message
         * @param code
         */
        public BussinessException(Integer code,String message) {
            super(message);
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
}