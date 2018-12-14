package com.github.demo.component.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {

    private final static long LOCK_EXPIRE = 10 * 1000L; //单个业务持有锁的时间10s,防止死锁

    private final static long LOCK_TRY_INTERVAL = 30L; //默认30ms尝试一次

    private final static long LOCK_TRY_TIMEOUT = 5 * 1000L; // 默认尝试30s

    @Autowired
    private StringRedisTemplate template;

    /**
     * 操作redis获取全局锁
     *
     * @param key            锁的名称
     * @param timeout        获取的超时时间
     * @param tryInterval    多少ms尝试一次
     * @param lockExpireTime 获取成功后锁的过期时间
     * @return true 获取成功，false获取失败
     */
    public boolean getLock(String key, long timeout, long tryInterval, long lockExpireTime) {
        try {
            if (!StringUtils.isEmpty(key)) {
                long startTime = System.currentTimeMillis();
                while (true) {
                    String lock = "lock";
                    if (template.opsForValue().setIfAbsent(key, lock)) {
                        template.opsForValue().set(key, lock, lockExpireTime, TimeUnit.MILLISECONDS);
                        return true;
                    }
                    if (System.currentTimeMillis() - startTime > timeout) {
                        return false;
                    }
                    Thread.sleep(tryInterval);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean tryLock(String key) {
        return getLock(key, LOCK_TRY_TIMEOUT, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }


    public boolean tryLock(String key, long timeout) {
        return getLock(key, timeout, LOCK_TRY_INTERVAL, LOCK_EXPIRE);
    }


    public boolean tryLock(String key, long timeout, long tryInterval) {
        return getLock(key, timeout, tryInterval, LOCK_EXPIRE);
    }


    public boolean tryLock(String key, long timeout, long tryInterval, long lockExpireTime) {
        return getLock(key, timeout, tryInterval, lockExpireTime);
    }

    public void releaseLock(String key) {
        if (!StringUtils.isEmpty(key)) {
            template.delete(key);
        }
    }

}
