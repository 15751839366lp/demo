/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.bean.Course;
import com.system.bean.Score;
import com.system.service.StudentServiceImpl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author asus
 */
@Controller
public class StudentController {

    @Autowired
    StudentServiceImpl studentServiceImpl;

    @GetMapping("/student/info")
    public String stuInfo(String name, String pwd, String stuNo, String pro, Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("pwd", pwd);
        map.put("stuNo", stuNo);
        map.put("pro", pro);
        model.addAttribute("map", map);
        return "student/info";
    }

    @RequestMapping("/student/scoreList")
    public String scoreList(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer stuId, Model model) {
        PageHelper.startPage(i, 12);
        List<Score> list = studentServiceImpl.getScoreByStuId(stuId);
        PageInfo<Score> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        model.addAttribute("map", map);
        return "student/scoreList";
    }

    @RequestMapping("/student/courseList")
    public String courseList(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer stuId, Model model) {
        PageHelper.startPage(i, 12);
        List<Score> list = studentServiceImpl.getScoreByStuId(stuId);
        PageInfo<Score> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        model.addAttribute("map", map);
        return "student/courseList";
    }

    @RequestMapping("/student/selectList")
    public String selectList(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer stuId, Model model) {
        PageHelper.startPage(i, 7);
        List<Course> list = studentServiceImpl.getSelectCourse(stuId);
        List<Course> myCourseList = studentServiceImpl.getMyCourseByStuId(stuId);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("myCourseList", myCourseList);
        map.put("selectCourseList", info);
        model.addAttribute("map", map);
        return "student/selectCourse";
    }

    @ResponseBody
    @RequestMapping("/student/choose")
    public String choose(@RequestParam("courseId") Integer courseId, @RequestParam("stuId") Integer stuId) {
        return studentServiceImpl.choose(stuId, courseId);
    }

    @ResponseBody
    @RequestMapping("/student/cancel")
    public String cancel(@RequestParam("courseId") Integer courseId, @RequestParam("stuId") Integer stuId) {
        return studentServiceImpl.cancel(stuId, courseId);
    }

}
