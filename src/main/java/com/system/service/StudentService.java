package com.system.service;

import com.system.bean.Course;
import com.system.bean.Score;

import java.util.List;

public interface StudentService {

    List<Score> getScoreByStuId(Integer stuId);

    List<Course> getSelectCourse(Integer stuId);

    List<Course> getMyCourseByStuId(Integer stuId);

    List<Course> getRecommendCourse(Integer stuId);

    String choose(Integer stuId, Integer courseId);

    String cancel(Integer stuId, Integer courseId);
}
