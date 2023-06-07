package com.bhole.shop.common.redis.util;

/**
 * @program: bhole-shop-common-redis
 * @description:
 * @author: joke
 * @date: 2023/6/7 16:12
 * @version: 1.0
 */
public interface DistributedLocker {

    /**
     * 获取分布式锁并根据获取结果执行代码-非阻塞锁-到期自动续约
     *
     * @param resourceName          需要加锁的业务名称
     * @param acquiredLockWorker    获取锁后执行的代码
     * @param notAcquiredLockWorker 未获取到锁执行的代码
     * @return 执行完代码的返回值
     * @throws Exception 异常
     */
    <T> T tryLocK(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, AcquiredLockWorker<T> notAcquiredLockWorker) throws Exception;

    /**
     * 获取分布式锁并根据获取结果执行代码-非阻塞锁-到期无法自动续约
     *
     * @param resourceName          需要加锁的业务名称
     * @param acquiredLockWorker    获取锁后执行的代码
     * @param notAcquiredLockWorker 未获取到锁执行的代码
     * @param lockTime              获取锁后的过期时间 单位秒
     * @return 执行完代码的返回值
     * @throws Exception 异常
     */
    <T> T tryLocK(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, AcquiredLockWorker<T> notAcquiredLockWorker, int lockTime) throws Exception;

    /**
     * 获取分布式锁并根据获取结果执行代码-阻塞锁-到期自动续约
     *
     * @param resourceName       需要加锁的业务名称
     * @param acquiredLockWorker 获取锁后执行的代码
     * @return 执行完代码的返回值
     * @throws Exception 异常
     */
    <T> T lock(String resourceName, AcquiredLockWorker<T> acquiredLockWorker) throws Exception;

    /**
     * 获取分布式锁并根据获取结果执行代码-阻塞锁-到期无法自动续约
     *
     * @param resourceName       需要加锁的业务名称
     * @param acquiredLockWorker 获取锁后执行的代码
     * @param lockTime           获取锁后的过期时间 单位秒
     * @return 执行完代码的返回值
     * @throws Exception 异常
     */
    <T> T lock(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, int lockTime) throws Exception;

}
