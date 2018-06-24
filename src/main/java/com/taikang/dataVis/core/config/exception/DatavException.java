package com.taikang.dataVis.core.config.exception;

import org.springframework.validation.annotation.Validated;

/**
 * @author iwt_gaoliang
 * @ClassName: CusException
 * @Description: (公共exception类，返回错误码和错误提示)
 * @date 2018年3月12日 下午1:58:10
 */
@Validated
public class DatavException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * @Fields code : (错误码)
     */
    private String code;
    /**
     * @Fields message : (错误提示)
     */
    private String message;

    public DatavException() {
        super();
    }

    public DatavException(String message) {
        this.message = message;
    }

    public DatavException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
