package com.github.demo.component.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    @Autowired
    private RedisTemplateFactory redisTemplateFactory;

    public <T> void put(String key, T value) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().opsForValue().set(key, value);
    }

    public <T> void put(String key, T value, Long timeout) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().opsForValue().set(key, value);
        redisTemplateFactory.createTemplate().expire(key, timeout, TimeUnit.SECONDS);
    }
    
    public <T> T get(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        RedisTemplate<String, T> redisTemplate = redisTemplateFactory.createTemplate();
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean hasKey(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        return redisTemplateFactory.createTemplate().hasKey(key);
    }

    public void delete(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().delete(key);
    }
    
	public void incrBy(String key, Long increment) {
		if (StringUtils.isBlank(key)) {
			throw new NullPointerException("redis key can't be empty...");
		}
		redisTemplateFactory.createTemplate().opsForValue().increment(key, increment);
	}

    public <T> void rListPush(String key, T value) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().opsForList().rightPush(key, value);
    }
    
    
	public List<Object> getAllList(String key) {
		if (StringUtils.isBlank(key)) {
			throw new NullPointerException("redis key can't be empty...");
		}
		return redisTemplateFactory.createTemplate().opsForList().range(key, 0, -1);
	}

    public <T> void lListPush(String key, T value) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().opsForList().leftPush(key, value);
    }

	public Long listSize(String key) {
		if (StringUtils.isBlank(key)) {
			throw new NullPointerException("redis key can't be empty...");
		}
		return redisTemplateFactory.createTemplate().opsForList().size(key);
	}

    
    public <T> T rListPop(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        RedisTemplate<String, T> redisTemplate = redisTemplateFactory.createTemplate();
        return redisTemplate.opsForList().rightPop(key);
    }

    public void watch(String... keys) {
        List<String> keyList = new ArrayList<>(Arrays.asList(keys));
        redisTemplateFactory.createTemplate().watch(keyList);
    }

    public void watch(String key) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().watch(key);
    }

    public <T> void addZet(String key, T value, double scores) {
        if (StringUtils.isBlank(key)) {
            throw new NullPointerException("redis key can't be empty...");
        }
        redisTemplateFactory.createTemplate().opsForZSet().add(key, value, scores);
    }


	public <T> T getTopSet(String key) {
		if (StringUtils.isBlank(key)) {
			throw new NullPointerException("redis key can't be empty...");
		}
		if (!this.hasKey(key)) {
			return null;
		} else {
			RedisTemplate<String, T> redisTemplate = redisTemplateFactory.createTemplate();
			Set<ZSetOperations.TypedTuple<T>> tuples = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, 0,
					Long.MAX_VALUE, 0, 1);
			if (!tuples.isEmpty()) {
				ZSetOperations.TypedTuple<T> t = tuples.iterator().next();
				return t.getValue();
			} else {
				return null;
			}

		}
	}
    
    
	public <K, V> RedisTemplate<K, V> template() {
		return redisTemplateFactory.createTemplate();
	}

}
