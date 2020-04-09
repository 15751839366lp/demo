/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.component;

import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.ContextLoader;

/**
 *
 * @author asus
 */
public class MyCache implements Cache{

    private String id;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    RedisTemplate<String, Object> template;
            
    public MyCache(){}
    
    public MyCache(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override//key是包含sql语句的字符串，value是查询的值
    public void putObject(Object key, Object value) {
        System.out.println("cache: " + key + ":" + value);
        template.opsForValue().set(key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        System.out.println("Check cache, key:" + key.getClass());
        Object cache = template.opsForValue().get(key.toString());
        if(cache != null){
            return cache;
        } else {
            System.out.println("Fail to find cache!");
            return null;
        }
    }

    @Override
    public Object removeObject(Object key) {
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        template.delete(key.toString());
        return null;
    }

    @Override
    public void clear() {
        System.out.println("Occur write,clear all cache!");
        template = (RedisTemplate) ContextLoader.getCurrentWebApplicationContext().getBean("redisTemplate");
        Set<String> keys = template.keys("*" + this.getId() + "*");
        template.delete(keys);
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.lock;
    }
    
}
