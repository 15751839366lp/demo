package com.system.service;

import com.system.bean.Course;
import com.system.bean.Score;
import com.system.bean.Student;
import com.system.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

public interface AdminService {
    ArrayList<Student> getStuList();

    void addStu(Student stu);

    Student getStuById(Integer id);

    void editStu(Student stu);

    void deleteStu(Integer id);

    ArrayList<Student> getStuByName(String userName);

    ArrayList<Student> getStuByStuNo(String stuNo);

    ArrayList<Student> getStuByPro(String pro);

    ArrayList<Teacher> getTeaList();

    void addTea(Teacher tea);

    Teacher getTeaById(Integer id);

    void editTea(Teacher tea);

    void deleteTea(Integer id);

    ArrayList<Teacher> getTeaByName(String userName);

    ArrayList<Teacher> getTeaByTeaNo(String teaNo);

    ArrayList<Course> getCourseList();

    void addCourse(Course course);

    Course getCourseById(Integer id);

    void editCourse(Course course);

    void deleteCourse(Integer id);

    ArrayList<Course> getCourseByName(String courseName);

    ArrayList<Course> getCourseByCourseType(String courseType);

    ArrayList<Course> getCourseByCredit(Integer credit);

    ArrayList<Course> getCourseByTeaName(String teaName);

    List<Score> getStuByCourseId(Integer courseId);

    void deleteScore(Integer id, Integer courseId);

    String addScore(Integer sid, Integer cid,Integer score);
}
