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
import com.system.mapper.AdminMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author asus
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public ArrayList<Student> getStuList() {
        return adminMapper.getStuList();
    }

    @Override
    public void addStu(Student stu) {
        adminMapper.addStu(stu);
    }

    @Override
    public Student getStuById(Integer id) {
        return adminMapper.getStuById(id);
    }

    @Override
    public void editStu(Student stu) {
        adminMapper.editStu(stu);
    }

    @Override
    public void deleteStu(Integer id) {
        adminMapper.deleteStu(id);
    }

    @Override
    public ArrayList<Student> getStuByName(String userName) {
        return adminMapper.getStuByName(userName);
    }

    @Override
    public ArrayList<Student> getStuByStuNo(String stuNo) {
        return adminMapper.getStuByStuNo(stuNo);
    }

    @Override
    public ArrayList<Student> getStuByPro(String pro) {
        return adminMapper.getStuByPro(pro);
    }

    @Override
    public ArrayList<Teacher> getTeaList() {
        return adminMapper.getTeaList();
    }

    @Override
    public void addTea(Teacher tea) {
        adminMapper.addTea(tea);
    }

    @Override
    public Teacher getTeaById(Integer id) {
        return adminMapper.getTeaById(id);
    }

    @Override
    public void editTea(Teacher tea) {
        adminMapper.editTea(tea);
    }

    @Override
    public void deleteTea(Integer id) {
        adminMapper.deleteTea(id);
    }

    @Override
    public ArrayList<Teacher> getTeaByName(String userName) {
        return adminMapper.getTeaByName(userName);
    }

    @Override
    public ArrayList<Teacher> getTeaByTeaNo(String teaNo) {
        return adminMapper.getTeaByTeaNo(teaNo);
    }

    @Override
    public ArrayList<Course> getCourseList() {
        return adminMapper.getCourseList();
    }

    @Override
    public void addCourse(Course course) {
        adminMapper.addCourse(course);
    }

    @Override
    public Course getCourseById(Integer id) {
        return adminMapper.getCourseById(id);
    }

    @Override
    public void editCourse(Course course) {
        adminMapper.editCourse(course);
    }

    @Override
    public void deleteCourse(Integer id) {
        adminMapper.deleteCourse(id);
    }

    @Override
    public ArrayList<Course> getCourseByName(String courseName) {
        return adminMapper.getCourseByName(courseName);
    }

    @Override
    public ArrayList<Course> getCourseByCourseType(String courseType) {
        return adminMapper.getCourseByCourseType(courseType);
    }

    @Override
    public ArrayList<Course> getCourseByCredit(Integer credit) {
        return adminMapper.getCourseByCredit(credit);
    }
    
    @Override
    public ArrayList<Course> getCourseByTeaName(String teaName) {
        return adminMapper.getCourseByTeaName(teaName);
    }

    @Override
    public List<Score> getStuByCourseId(Integer courseId) {
        return adminMapper.getStuByCourseId(courseId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteScore(Integer id, Integer courseId) {
        adminMapper.reduceStuNum(courseId);
        adminMapper.deleteScore(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addScore(Integer sid, Integer cid,Integer score) {
        Course course = adminMapper.getCourseById(cid);
        if(course.getStuNum() >= course.getSelectNum()){
            return "人数已满，添加失败！";
        }else{
            adminMapper.addStuNum(cid);
            adminMapper.addScore(sid,cid,score);
            return "添加成功！";
        }
    }
}
