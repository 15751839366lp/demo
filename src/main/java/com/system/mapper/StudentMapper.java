/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.mapper;

import com.system.bean.Course;
import com.system.bean.Score;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author asus
 */
@Mapper
@Repository
public interface StudentMapper {

    List<Score> getScoreByStuId(Integer stuId);

    List<Course> getSelectCourse(Integer stuId);

    List<Course> getMyCourseByStuId(Integer stuId);

    void addSelectCourse(@Param("stuId") Integer stuId, @Param("courseId") Integer courseId);

    void deleteSelectCourse(@Param("stuId") Integer stuId, @Param("courseId") Integer courseId);

    List<Course> getAllCourse();

    List<Course> getRecommendCourse(@Param("stuId") Integer stuId,@Param("courseList") List<Integer> courseList);

    void addStuNum(Integer courseId);

    void reduceStuNum(Integer courseId);
}
