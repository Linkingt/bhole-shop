package com.bhole.shop.common.base.exception;

import java.io.Serial;
import java.util.Map;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/6/5 17:55
 * @version: 1.0
 */
public class CustomException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -71122580919163927L;
    private String msg;
    private Integer code;
    private Map<String, Object> extraData;

    public CustomException(final String message, Object ... org) {
        super(String.format(message, org));
        this.code = 500;
        this.msg = message;
    }

    public CustomException(String message, Throwable cause, Object ... org) {
        super(String.format(message, org), cause);
    }

    public CustomException(final Integer code, final String message, Object ... org) {
        super(String.format(message, org));
        this.code = code;
        this.msg = message;
    }

    public CustomException(final String message, final Map<String, Object> extraData, Object ... org) {
        super(String.format(message, org));
        this.code = 500;
        this.msg = message;
        this.extraData = extraData;
    }

    public CustomException(final Integer code, final String message, final Map<String, Object> extraData, Object ... org) {
        super(String.format(message, org));
        this.code = code;
        this.msg = message;
        this.extraData = extraData;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return msg;
    }

    public Map<String, Object> getExtraData() {
        return this.extraData;
    }
}