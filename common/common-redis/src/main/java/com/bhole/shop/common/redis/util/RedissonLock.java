package com.bhole.shop.common.redis.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @program: bhole-shop-common-redis
 * @description:
 * @author: joke
 * @date: 2023/6/7 16:12
 * @version: 1.0
 */
@Service
public class RedissonLock implements DistributedLocker {

    private static final String LOCKER_PREFIX = "lock:";

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public <T> T tryLocK(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, AcquiredLockWorker<T> notAcquiredLockWorker) throws Exception {
        return tryLocK(resourceName, acquiredLockWorker, notAcquiredLockWorker, -1);
    }

    @Override
    public <T> T tryLocK(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, AcquiredLockWorker<T> notAcquiredLockWorker, int lockTime) throws Exception {
        RLock lock = redissonClient.getLock(LOCKER_PREFIX + resourceName);
        boolean flag = lockTime == -1 ? lock.tryLock() : lock.tryLock(lockTime, TimeUnit.SECONDS);
        if (!flag) {
            return notAcquiredLockWorker.invokeAfterLockAcquire();
        }
        try {
            return acquiredLockWorker.invokeAfterLockAcquire();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public <T> T lock(String resourceName, AcquiredLockWorker<T> acquiredLockWorker) throws Exception {
        return lock(resourceName, acquiredLockWorker, -1);
    }

    @Override
    public <T> T lock(String resourceName, AcquiredLockWorker<T> acquiredLockWorker, int lockTime) throws Exception {
        RLock lock = redissonClient.getLock(LOCKER_PREFIX + resourceName);
        if (lockTime == -1) {
            lock.lock();
        } else {
            lock.lock(lockTime, TimeUnit.SECONDS);
        }
        try {
            return acquiredLockWorker.invokeAfterLockAcquire();
        } finally {
            lock.unlock();
        }
    }
}
