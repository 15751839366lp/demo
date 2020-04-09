/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.service;

import com.system.bean.Course;
import com.system.bean.Score;
import com.system.component.RedisLock;
import com.system.mapper.StudentMapper;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import jdk.internal.org.objectweb.asm.Handle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author asus
 */
@Transactional
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisLock redisLock;

    private final String PREFIX = "course_selectNum";
    private final String REDISLOCKPREFIX = "redis_lock_course_prefix_";
    private ConcurrentHashMap<Integer, Boolean> courseFull = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        List<Course> list = studentMapper.getAllCourse();
        for (Course c : list) {
            redisTemplate.opsForValue().set(PREFIX + c.getId(), c.getSelectNum() + "");
        }
    }

    @Override
    public List<Score> getScoreByStuId(Integer stuId) {
        return studentMapper.getScoreByStuId(stuId);
    }

    @Override
    public List<Course> getSelectCourse(Integer stuId) {
        return studentMapper.getSelectCourse(stuId);
    }

    @Override
    public List<Course> getMyCourseByStuId(Integer stuId) {
        return studentMapper.getMyCourseByStuId(stuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String choose(Integer stuId, Integer courseId) {
        if (courseFull.get(courseId) != null) {
            return "选课已满！";
        }
        int retryTimes = 3;
        String expireTime = "1000";
        String requestId = UUID.randomUUID().toString();
        while (true) {
            if (redisLock.lock(REDISLOCKPREFIX + courseId, requestId, retryTimes, expireTime)) {
                try {
                    Long selectNum = redisTemplate.opsForValue().decrement(PREFIX + courseId, 1);
                    if (selectNum < 0) {
                        courseFull.put(courseId, true);
                        redisTemplate.opsForValue().increment(PREFIX + courseId, 1);
                        return "选课已满！";
                    }

                    studentMapper.addStuNum(courseId);
                    studentMapper.addSelectCourse(stuId, courseId);
                } catch (Exception e) {
                    redisTemplate.opsForValue().increment(PREFIX + courseId, 1);
                    if (courseFull.get(courseId) != null) {
                        courseFull.remove(courseId);
                    }
                    return "选课失败！";
                } finally {
                    redisLock.unlock(REDISLOCKPREFIX + courseId, requestId);
                }
                return "选课成功！";
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancel(Integer stuId, Integer courseId) {
        int retryTimes = 3;
        String expireTime = "1000";
        String requestId = UUID.randomUUID().toString();
        while (true) {
            if (redisLock.lock(REDISLOCKPREFIX + courseId, requestId, retryTimes, expireTime)) {
                Long selectNum = redisTemplate.opsForValue().increment(PREFIX + courseId, 1);
                try {
                    studentMapper.reduceStuNum(courseId);
                    studentMapper.deleteSelectCourse(stuId, courseId);
                } catch (Exception e) {
                    redisTemplate.opsForValue().decrement(PREFIX + courseId, 1);
                    if (courseFull.get(courseId) != null) {
                        courseFull.remove(courseId);
                    }
                    return "退选失败！";
                } finally {
                    redisLock.unlock(REDISLOCKPREFIX + courseId, requestId);
                }
                return "退选成功！";
            }
        }
    }

}
