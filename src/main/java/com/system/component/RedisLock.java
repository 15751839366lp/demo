/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.component;

import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisLock {
    
    private Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    private static final String RELEASE_SUCCESS = "1";

    private static final String UNLOCK_LUA;
    private static final String LOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] then");
        sb.append("    return tostring(redis.call(\"del\",KEYS[1])) ");
        sb.append("else ");
        sb.append("    return \"0\" ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"set\",KEYS[1],ARGV[1],\"NX\",\"PX\",ARGV[2])");
        sb.append("then ");
        sb.append("    return \"1\" ");
        sb.append("else ");
        sb.append("    return \"0\" ");
        sb.append("end ");
        LOCK_LUA = sb.toString();
    }

    private static final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


    public boolean lock(String key, String requestId, int retryTimes, String expireTime) {
        if (retryTimes <= 0) {
            retryTimes = 1;
        }
        try {
            int count = 0;
            while (true) {
                // 执行set命令
                DefaultRedisScript<String> stringDefaultRedisScript = lockRedisScript();
                String absent = redisTemplate.execute(stringDefaultRedisScript, stringRedisSerializer, stringRedisSerializer, Collections.singletonList(key), requestId, expireTime);
                //是否成功获取锁
                if (RELEASE_SUCCESS.equals(absent)) {
                    return true;
                } else {
                    count++;
                    if (retryTimes == count) {
                        logger.warn("has tried {} times , failed to acquire lock for key:{},requestId:{}", count, key, requestId);
                        return false;
                    } else {
                        logger.warn("try to acquire lock {} times for key:{},requestId:{}", count, key, requestId);
                        Thread.sleep(100);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解锁
     */
    public boolean unlock(String key, String requestId) {
        //使用Lua脚本：先判断是否是自己设置的锁，再执行删除
        DefaultRedisScript<String> stringDefaultRedisScript = unLockRedisScript();
        String result = redisTemplate.execute(stringDefaultRedisScript, stringRedisSerializer, stringRedisSerializer, Collections.singletonList(key), requestId);
        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    @Bean
    public DefaultRedisScript<String> unLockRedisScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptText(UNLOCK_LUA);
//      defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("delete.lua")));
        return defaultRedisScript;
    }

    @Bean
    public DefaultRedisScript<String> lockRedisScript() {
        DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(String.class);
        defaultRedisScript.setScriptText(LOCK_LUA);
//      defaultRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("delete.lua")));
        return defaultRedisScript;
    }
}
