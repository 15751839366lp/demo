package com.system.service;

import com.system.bean.Course;
import com.system.bean.Score;
import com.system.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public interface TeacherService {
    ArrayList<Course> getCourseList();

    void addCourse(Course course);

    Course getCourseById(Integer id);

    List<Course> getCourseByTeaId(Integer teaId);

    void editCourse(Course course);

    void deleteCourse(Integer id);

    ArrayList<Course> getCourseByName(String courseName,Integer teaId);

    ArrayList<Course> getCourseByCourseType(String courseType, Integer teaId);

    ArrayList<Course> getCourseByCredit(Integer credit, Integer teaId);

    ArrayList<Teacher> getTeaByTeaNo(String teaNo);

    ArrayList<Score> getScoreByTeaId(Integer teaId);

    void editScore(Integer id,Integer score);

    ArrayList<Course> getSelectCourseByTeaId(Integer teaId);

    Course getSelectCourseById(Integer id);

    void editSelectCourse(Course course);

    void deleteSelectCourse(Integer id);

    void addSelectCourse(Course course);

    void courseInput(Integer id,Integer teaId);

    void addScore(Integer sid,Integer cid);
}
