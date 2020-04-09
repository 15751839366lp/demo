/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.system.bean.Course;
import com.system.bean.Score;
import com.system.bean.Student;
import com.system.bean.Teacher;
import com.system.service.AdminServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.system.service.TeacherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author asus
 */
@Controller
public class AdminController {

    @Autowired
    AdminServiceImpl adminServiceImpl;
    @Autowired
    TeacherServiceImpl teacherServiceImpl;

    ObjectMapper mapper = new ObjectMapper();

    //学生表的操作
    @RequestMapping("/stuList")
    public String stuList(@RequestParam(value = "page", defaultValue = "1") Integer i, Model model) {
        PageHelper.startPage(i, 12);//只需引用这个就可以分页 
        List<Student> list = adminServiceImpl.getStuList();
        //将查询到的list放入PageInfo，可以获得所有信息
        PageInfo<Student> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("stuList", info);
        model.addAttribute("map", map);
        return "student/list";
    }

    @RequestMapping(value = "/stu", method = RequestMethod.POST)
    public String addStu(String userName, String password, String stuNo, String professional) {
        Student stu = new Student(null, userName, password, stuNo, professional, "学生");
        ArrayList<Student> stuList = adminServiceImpl.getStuList();
        for (Student student : stuList) {
            if (Objects.equals(student.getStuNo(), stuNo)) {
                return "redirect:/student/save.jsp";
            }
        }
        adminServiceImpl.addStu(stu);
        return "redirect:/stuList";
    }

    @RequestMapping(value = "/stu/{id}", method = RequestMethod.GET)
    public String toEditStu(@PathVariable("id") Integer id, Model model) {
        Student stu = adminServiceImpl.getStuById(id);
        model.addAttribute("stu", stu);
        return "student/edit";
    }

    @PutMapping(value = "/stu/{id}")
    public String editStu(@PathVariable("id") Integer id, String userName, String password, String stuNo, String professional) {
        Student stu = new Student(id, userName, password, stuNo, professional, "学生");
        ArrayList<Student> stuList = adminServiceImpl.getStuList();
        for (Student student : stuList) {
            if (!Objects.equals(student.getId(), id)) {
                if (Objects.equals(student.getStuNo(), stuNo)) {
                    return "redirect:/stu/" + id;
                }
            }
        }
        adminServiceImpl.editStu(stu);
        return "redirect:/stuList";
    }

    @DeleteMapping(value = "/stu/{id}")
    public String deleteStu(@PathVariable("id") Integer id) {
        adminServiceImpl.deleteStu(id);
        return "redirect:/stuList";
    }

    //三种查询
    @RequestMapping(value = "/getStuByName")
    public String getStuByName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("name") String userName, Model model) {
        PageHelper.startPage(i, 12);
        List<Student> list = adminServiceImpl.getStuByName(userName);
        PageInfo<Student> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("stuList", info);
        map.put("method", "name");
        map.put("value", userName);
        model.addAttribute("map", map);
        return "student/list";
    }

    @RequestMapping(value = "/getStuByStuNo")
    public String getStuByStuNo(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("stuNo") String stuNo, Model model) {
        PageHelper.startPage(i, 12);
        List<Student> list = adminServiceImpl.getStuByStuNo(stuNo);
        PageInfo<Student> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("stuList", info);
        map.put("method", "stuNo");
        map.put("value", stuNo);
        model.addAttribute("map", map);
        return "student/list";
    }

    @RequestMapping(value = "/getStuByPro")
    public String getStuByPro(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("pro") String pro, Model model) {
        PageHelper.startPage(i, 12);
        List<Student> list = adminServiceImpl.getStuByPro(pro);
        PageInfo<Student> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("stuList", info);
        map.put("method", "pro");
        map.put("value", pro);
        model.addAttribute("map", map);
        return "student/list";
    }

    //教师表的操作
    @RequestMapping("/teaList")
    public String teaList(@RequestParam(value = "page", defaultValue = "1") Integer i, Model model) {
        PageHelper.startPage(i, 12);//只需引用这个就可以分页 
        List<Teacher> list = adminServiceImpl.getTeaList();
        //将查询到的list放入PageInfo，可以获得所有信息
        PageInfo<Teacher> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teaList", info);
        model.addAttribute("map", map);
        return "teacher/list";
    }

    @RequestMapping(value = "/tea", method = RequestMethod.POST)
    public String addTea(String userName, String password, String teaNo) {
        Teacher tea = new Teacher(null, userName, password, teaNo, "教师");
        ArrayList<Teacher> teaList = adminServiceImpl.getTeaList();
        for (Teacher teacher : teaList) {
            if (Objects.equals(teacher.getTeaNo(), teaNo)) {
                return "redirect:/teacher/save.jsp";
            }
        }
        adminServiceImpl.addTea(tea);
        return "redirect:/teaList";
    }

    @RequestMapping(value = "/tea/{id}", method = RequestMethod.GET)
    public String toEditTea(@PathVariable("id") Integer id, Model model) {
        Teacher tea = adminServiceImpl.getTeaById(id);
        model.addAttribute("tea", tea);
        return "teacher/edit";
    }

    @RequestMapping(value = "/tea/{id}", method = RequestMethod.PUT)
    public String editTea(@PathVariable("id") Integer id, String userName, String password, String teaNo) {
        Teacher tea = new Teacher(id, userName, password, teaNo, "教师");
        ArrayList<Teacher> teaList = adminServiceImpl.getTeaList();
        for (Teacher teacher : teaList) {
            if (!Objects.equals(teacher.getId(), id)) {
                if (Objects.equals(teacher.getTeaNo(), teaNo)) {
                    return "redirect:/tea/" + id;
                }
            }
        }
        adminServiceImpl.editTea(tea);
        return "redirect:/teaList";
    }

    //@ResponseBody
    @RequestMapping(value = "/tea/{id}", method = RequestMethod.DELETE)
    public String deleteTea(@PathVariable("id") Integer id) {
        adminServiceImpl.deleteTea(id);
        return "redirect:/teaList";
    }

    //三种查询
    @RequestMapping(value = "/getTeaByName")
    public String getTeaByName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("name") String userName, Model model) {
        PageHelper.startPage(i, 12);
        List<Teacher> list = adminServiceImpl.getTeaByName(userName);
        PageInfo<Teacher> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teaList", info);
        map.put("method", "name");
        map.put("value", userName);
        model.addAttribute("map", map);
        return "teacher/list";
    }

    @RequestMapping(value = "/getTeaByTeaNo")
    public String getTeaByTeaNo(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("teaNo") String teaNo, Model model) {
        PageHelper.startPage(i, 12);
        List<Teacher> list = adminServiceImpl.getTeaByTeaNo(teaNo);
        PageInfo<Teacher> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teaList", info);
        map.put("method", "teaNo");
        map.put("value", teaNo);
        model.addAttribute("map", map);
        return "teacher/list";
    }

    //课程操作
    @RequestMapping("/courseList")//哲学,经济学,法学,教育学,文学,历史学,理学,工学,农学,医学,军事学,管理学
    public String courseList(@RequestParam(value = "page", defaultValue = "1") Integer i, Model model) {
        PageHelper.startPage(i, 12);//只需引用这个就可以分页 
        List<Course> list = adminServiceImpl.getCourseList();
        //将查询到的list放入PageInfo，可以获得所有信息
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        model.addAttribute("map", map);
        return "course/list";
    }

    @RequestMapping("/course/toAddCourse")
    public String toAddCourse(String teaNo, Model model) {
        model.addAttribute("teaNo", null);
        return "course/save";
    }

    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public String addCourse(String courseName, String courseType, Integer credit, String teaNo, Integer selectNum) {
        if (adminServiceImpl.getTeaByTeaNo(teaNo) != null) {
            Course course = new Course(null, courseName, courseType, credit, adminServiceImpl.getTeaByTeaNo(teaNo).get(0), null, selectNum);
            adminServiceImpl.addCourse(course);
            return "redirect:/courseList";
        } else {
            return "course/save";
        }
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.GET)
    public String toEditCourse(@PathVariable("id") Integer id, Model model) {
        Course course = adminServiceImpl.getCourseById(id);
        model.addAttribute("course", course);
        return "course/edit";
    }

    @RequestMapping(value = "/course/{id}", method = RequestMethod.PUT)
    public String editCourse(@PathVariable("id") Integer id, String courseName, String courseType, Integer credit, String teaNo, Integer selectNum) {
        Course course = new Course(id, courseName, courseType, credit, adminServiceImpl.getTeaByTeaNo(teaNo).get(0), null, selectNum);
        adminServiceImpl.editCourse(course);
        return "redirect:/courseList";
    }

    //@ResponseBody
    @RequestMapping(value = "/course/{id}", method = RequestMethod.DELETE)
    public String deleteCourse(@PathVariable("id") Integer id) {
        adminServiceImpl.deleteCourse(id);
        return "redirect:/courseList";
    }

    //四种查询
    @RequestMapping(value = "/getCourseByName")
    public String getCourseByName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("name") String courseName, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = adminServiceImpl.getCourseByName(courseName);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "name");
        map.put("value", courseName);
        model.addAttribute("map", map);
        return "course/list";
    }

    @RequestMapping(value = "/getCourseByTeaName")
    public String getCourseByTeaName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("teaName") String teaName, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = adminServiceImpl.getCourseByTeaName(teaName);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "teaName");
        map.put("value", teaName);
        model.addAttribute("map", map);
        return "course/list";
    }

    @RequestMapping(value = "/getCourseByCourseType")
    public String getCourseByCourseType(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("type") String courseType, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = adminServiceImpl.getCourseByCourseType(courseType);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "type");
        map.put("value", courseType);
        model.addAttribute("map", map);
        return "course/list";
    }

    @RequestMapping(value = "/getCourseByCredit")
    public String getCourseByCredit(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("credit") Integer credit, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = adminServiceImpl.getCourseByCredit(credit);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "credit");
        map.put("value", credit);
        model.addAttribute("map", map);
        return "course/list";
    }

    @RequestMapping(value = "/showStudent")
    public String showStudent(@RequestParam("id") Integer courseId, @RequestParam(value = "page", defaultValue = "1") Integer i, Model model) {
        PageHelper.startPage(i, 7);
        List<Score> stus = adminServiceImpl.getStuByCourseId(courseId);
        PageInfo<Score> info = new PageInfo<>(stus);
        HashMap<String, Object> map = new HashMap<>();
        Course course = adminServiceImpl.getCourseById(courseId);
        map.put("course", course);
        map.put("scoreList", info);
        map.put("courseId", courseId);
        model.addAttribute("map", map);
        return "course/courseInfo";
    }

    @RequestMapping("/course/toEditScore")
    public String toEditScore(@RequestParam("id") Integer id, @RequestParam("teaId") Integer teaId, Model model) {
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        for (Score score : list) {
            if (Objects.equals(score.getId(), id)) {
                model.addAttribute("score", score);
            }
        }
        return "/course/scoreInfo";
    }

    @PostMapping(value = "/course/score")
    public String editScore(@RequestParam("id") Integer id, Integer score, Integer courseId) {
        teacherServiceImpl.editScore(id, score);
        return "redirect:/showStudent?id=" + courseId;
    }

    @DeleteMapping("/score/{id}")
    public String deleteScore(@PathVariable("id") Integer id, Integer courseId) {
        adminServiceImpl.deleteScore(id, courseId);
        return "redirect:/showStudent?id=" + courseId;
    }

    @GetMapping("/toAddScore")
    public String toAddScore(Integer courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "course/addScore";
    }

    @ResponseBody
    @PostMapping("/score")
    public String addScore(String stuName, String stuNo, Integer cid, Integer score) {
        List<Student> stuListForName = adminServiceImpl.getStuByName(stuName);
        List<Student> stuList = adminServiceImpl.getStuByStuNo(stuNo);
        List<Score> courseScoreList = adminServiceImpl.getStuByCourseId(cid);
        for (Score s : courseScoreList) {
            if (s.getStu().getStuNo().equals(stuNo)) {
                return "该学生选此课程，请勿重复添加";
            }
        }

        if (stuList.isEmpty() || stuListForName.isEmpty()) {
            return "不存在该学生，请重新添加";
        } else if (!stuList.get(0).getUserName().equals(stuName)) {
            return "学生姓名与学号不匹配，请重新输入";
        } else {
            return adminServiceImpl.addScore(stuList.get(0).getId(), cid, score);
        }

    }
//    @RequestMapping("/getPage")
//    public String test1(@RequestParam(value = "page", defaultValue = "1") Integer i, Model model) {
//        if (i < 1) {
//            i = 1;
//        }
//        PageHelper.startPage(i, 5);//只需引用这个就可以分页 
//        List<Student> list = adminService.getStuList();
//        //将查询到的list放入PageInfo，可以获得所有信息
//        PageInfo<Student> info = new PageInfo<>(list);
//        System.out.println("当前页码：" + info.getPageNum());
//        System.out.println("总页码数：" + info.getPages());
//        System.out.println("总记录数：" + info.getTotal());
//        System.out.println("当前页有几天记录：" + info.getSize());
//        System.out.println("当前页码前一页：" + info.getPrePage());
//        System.out.println("结果：" + info.getList());//相当于list
//
//        model.addAttribute("teachers", info);
//        return "test3/page";
//    }
}
