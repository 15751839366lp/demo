/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.mapper;

import com.system.bean.Course;
import com.system.bean.Score;
import com.system.bean.Student;
import com.system.bean.Teacher;
import java.util.ArrayList;
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
public interface TeacherMapper {

    ArrayList<Course> getCourseList();

    void addCourse(Course course);

    Course getCourseById(Integer id);

    List<Course> getCourseByTeaId(Integer teaId);

    void editCourse(Course course);

    void deleteCourse(Integer id);

    ArrayList<Course> getCourseByName(@Param("courseName") String courseName, @Param("teaId") Integer teaId);

    ArrayList<Course> getCourseByCourseType(@Param("courseType") String courseType, @Param("teaId") Integer teaId);

    ArrayList<Course> getCourseByCredit(@Param("credit") Integer credit, @Param("teaId") Integer teaId);

    ArrayList<Teacher> getTeaByTeaNo(String teaNo);

    ArrayList<Score> getScoreByTeaId(Integer teaId);

    void editScore(@Param("id") Integer id, @Param("score") Integer score);

    ArrayList<Course> getSelectCourseByTeaId(Integer teaId);

    void editSelectCourse(Course course);

    Course getSelectCourseById(Integer id);

    void deleteSelectCourse(Integer id);

    void addSelectCourse(Course course);

    List<Score> getSelectCourse(Integer id);

    void addScore(@Param("sid")Integer sid,@Param("cid") Integer cid);

    Integer getCourse(Course course);

}
