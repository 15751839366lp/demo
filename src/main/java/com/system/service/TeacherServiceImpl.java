/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.service;

import com.system.bean.Course;
import com.system.bean.Score;
import com.system.bean.Student;
import com.system.bean.Teacher;
import com.system.mapper.TeacherMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author asus
 */
@Transactional
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    
    @Override
    public ArrayList<Course> getCourseList() {
        return teacherMapper.getCourseList();
    }

    @Override
    public void addCourse(Course course) {
        teacherMapper.addCourse(course);
    }

    @Override
    public Course getCourseById(Integer id) {
        return teacherMapper.getCourseById(id);
    }
    
    @Override
    public List<Course> getCourseByTeaId(Integer teaId) {
        return teacherMapper.getCourseByTeaId(teaId);
    }

    @Override
    public void editCourse(Course course) {
        teacherMapper.editCourse(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        teacherMapper.deleteCourse(id);
    }
    
    @Override
    public ArrayList<Course> getCourseByName(String courseName, Integer teaId) {
        return teacherMapper.getCourseByName(courseName, teaId);
    }

    @Override
    public ArrayList<Course> getCourseByCourseType(String courseType, Integer teaId) {
        return teacherMapper.getCourseByCourseType(courseType, teaId);
    }

    @Override
    public ArrayList<Course> getCourseByCredit(Integer credit, Integer teaId) {
        return teacherMapper.getCourseByCredit(credit, teaId);
    }

    
    @Override
    public ArrayList<Teacher> getTeaByTeaNo(String teaNo) {
        return teacherMapper.getTeaByTeaNo(teaNo);
    }
    
    @Override
    public ArrayList<Score> getScoreByTeaId(Integer teaId) {
        return teacherMapper.getScoreByTeaId(teaId);
    }
    
    @Override
    public void editScore(Integer id, Integer score){
        teacherMapper.editScore(id,score);
    }
    
    @Override
    public ArrayList<Course> getSelectCourseByTeaId(Integer teaId) {
        return teacherMapper.getSelectCourseByTeaId(teaId);
    }

    @Override
    public Course getSelectCourseById(Integer id) {
        return teacherMapper.getSelectCourseById(id);
    }

    @Override
    public void editSelectCourse(Course course) {
        teacherMapper.editSelectCourse(course);
    }

    @Override
    public void deleteSelectCourse(Integer id) {
        teacherMapper.deleteSelectCourse(id);
    }
    
    @Override
    public void addSelectCourse(Course course) {
        teacherMapper.addSelectCourse(course);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void courseInput(Integer id,Integer teaId) {
        Course course = teacherMapper.getSelectCourseById(id);
        teacherMapper.addCourse(course);
        List<Score> scores = teacherMapper.getSelectCourse(id);
        Integer cid = teacherMapper.getCourse(course);
        for(Score score : scores){
            score.getCourse().setId(cid);
            teacherMapper.addScore(score.getStu().getId(), cid);
        }
        teacherMapper.deleteSelectCourse(id);
    }

    @Override
    public void addScore(Integer sid, Integer cid) {
        teacherMapper.addScore(sid, cid);
    }
}
