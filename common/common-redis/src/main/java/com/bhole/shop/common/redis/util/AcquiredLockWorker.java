package com.bhole.shop.common.redis.util;

/**
 * @program: bhole-shop-common-redis
 * @description:
 * @author: joke
 * @date: 2023/6/7 16:12
 * @version: 1.0
 */
public interface AcquiredLockWorker<T> {

    /**
     * 获取锁后的执行逻辑
     *
     * @return
     */
    T invokeAfterLockAcquire() throws Exception;
}
