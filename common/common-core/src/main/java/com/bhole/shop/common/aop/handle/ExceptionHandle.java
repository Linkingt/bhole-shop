package com.bhole.shop.common.aop.handle;

import com.bhole.shop.common.base.bean.ResultBean;
import com.bhole.shop.common.base.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: bhole-shop-common-core
 * @description:
 * @author: joke
 * @date: 2023/6/6 16:11
 * @version: 1.0
 */

@ControllerAdvice
public class ExceptionHandle {

    @Value("${spring.profiles.active:DEV}")
    private String active;

    public ExceptionHandle() {
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResultBean<Void> handle(Exception e) {
        if ("DEV".equalsIgnoreCase(this.active)) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        if (e instanceof CustomException userException) {
            return ResultBean.fail(userException.getCode(), userException.getMessage());
        } else {
            return ResultBean.fail(e.getMessage());
        }
    }
}
