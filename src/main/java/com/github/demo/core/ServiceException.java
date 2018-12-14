package com.github.demo.core;


import com.zhaoonline.zhaotask.core.constants.PlateFormErrorCode;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -2113706917862971597L;
    private ResultCode code;
    private String message;

    public ServiceException(ResultCode code) {
        this.code = code;
    }

    public ServiceException(PlateFormErrorCode code) {
        this.message = code.getValue();
    }

    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.INTERNAL_SERVER_ERROR;
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
