package com.bhole.shop.common.base.entity;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @program: bhole-shop-common-base
 * @description:
 * @author: joke
 * @date: 2023/6/5 16:28
 * @version: 1.0
 */
public class UserLocal {

    private static final Logger logger = LoggerFactory.getLogger(UserLocal.class);

    private static TransmittableThreadLocal<UserInfo> local = new TransmittableThreadLocal<>();

    public static void set(UserInfo user) {
        local.set(user);
        logger.info("> User info:{}", local.get());
    }

    public static UserInfo get() {
        return local.get();
    }

    public static Long getUserId() {
        UserInfo user = local.get();
        return Objects.nonNull(user) ? user.getUserId() : null;
    }

    public static String getUserName() {
        UserInfo user = local.get();
        return Objects.nonNull(user) ? user.getUserName() : null;
    }

    public static void remove() {
        local.remove();
    }
}
