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
import com.system.service.TeacherServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author asus
 */
@Controller
public class TeacherController {

    @Autowired
    TeacherServiceImpl teacherServiceImpl;

    @RequestMapping("/teacher/info")
    public String teaInfo(String name, String pwd, String teaNo, Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("pwd", pwd);
        map.put("teaNo", teaNo);
        model.addAttribute("map", map);
        return "teacher/info";
    }

    @RequestMapping("/teacher/courseList")
    public String courseList(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);//只需引用这个就可以分页 
        List<Course> list = teacherServiceImpl.getCourseByTeaId(teaId);
        //将查询到的list放入PageInfo，可以获得所有信息
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        model.addAttribute("map", map);
        if (teaId == null) {
            return "login";
        }
        return "teacher/courseList";
    }

    @RequestMapping("/teacher/course/toAddCourse")
    public String toAddCourse(String teaNo, Model model) {
        model.addAttribute("teaNo", teaNo);
        return "course/save";
    }

//    @RequestMapping(value = "/teacher/course", method = RequestMethod.POST)
//    public String addCourse(String courseName, String courseType, Integer credit, String teaNo) {
//        if (teacherService.getTeaByTeaNo(teaNo) != null) {
//            Course course = new Course(null, courseName, courseType, credit, teacherService.getTeaByTeaNo(teaNo).get(0), null);
//            teacherService.addCourse(course);
//            return "redirect:/teacher/courseList?id="+teacherService.getTeaByTeaNo(teaNo).get(0).getId();
//        } else {
//            return "course/save";
//        }
//    }
    @RequestMapping(value = "/teacher/course/{id}", method = RequestMethod.GET)
    public String toEditCourse(@PathVariable("id") Integer id, Model model) {
        Course course = teacherServiceImpl.getCourseById(id);
        model.addAttribute("course", course);
        return "course/edit";
    }

    @RequestMapping(value = "/teacher/course/{id}", method = RequestMethod.PUT)
    public String editCourse(@PathVariable("id") Integer id, String courseName, String courseType, Integer credit, String teaNo,Integer selectNum) {
        Course course = new Course(id, courseName, courseType, credit, teacherServiceImpl.getTeaByTeaNo(teaNo).get(0),null,selectNum);
        teacherServiceImpl.editCourse(course);
        return "redirect:/teacher/courseList?id=" + teacherServiceImpl.getTeaByTeaNo(teaNo).get(0).getId();
    }

    //@ResponseBody
    @RequestMapping(value = "/teacher/course/{id}", method = RequestMethod.DELETE)
    public String deleteCourse(@PathVariable("id") Integer id, @RequestParam("teaId") Integer teaId) {
        teacherServiceImpl.deleteCourse(id);

        return "redirect:/teacher/courseList?id=" + teaId;
    }

    //四种查询
    @RequestMapping(value = "/teacher/getCourseByName")
    public String getCourseByName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("name") String courseName, @RequestParam("teaId") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = teacherServiceImpl.getCourseByName(courseName, teaId);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "name");
        map.put("value", courseName);
        model.addAttribute("map", map);

        return "teacher/courseList";
    }

    @RequestMapping(value = "/teacher/getCourseByCourseType")
    public String getCourseByCourseType(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("type") String courseType, @RequestParam("teaId") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = teacherServiceImpl.getCourseByCourseType(courseType, teaId);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "type");
        map.put("value", courseType);
        model.addAttribute("map", map);

        return "teacher/courseList";
    }

    @RequestMapping(value = "/teacher/getCourseByCredit")
    public String getCourseByCredit(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("credit") Integer credit, @RequestParam("teaId") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);
        List<Course> list = teacherServiceImpl.getCourseByCredit(credit, teaId);
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        map.put("method", "credit");
        map.put("value", credit);
        model.addAttribute("map", map);

        return "teacher/courseList";
    }

    @RequestMapping("/teacher/scoreList")
    public String stuList(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        Set<String> courseSet = new HashSet<String>();
        for (Score score : list) {
            courseSet.add(score.getCourse().getCourseName());
        }
        PageInfo<Score> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("scoreList", info);
        map.put("courseSet", courseSet);
        map.put("teaId", teaId);
        model.addAttribute("map", map);

        return "teacher/studentList";
    }

    @RequestMapping("/teacher/getScoreByCourseName")
    public String getScoreByCourseName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("courseName") String courseName, @RequestParam("teaId") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        List<Score> newList = new ArrayList<Score>();
        if ("所有课程".equals(courseName)) {
            return "redirect:/teacher/scoreList?id=" + teaId;
        } else {
            Set<String> courseSet = new HashSet<String>();
            for (Score score : list) {
                courseSet.add(score.getCourse().getCourseName());
            }
            for (Score score : list) {
                if (Objects.equals(score.getCourse().getCourseName(), courseName)) {
                    newList.add(score);
                }
            }
            PageInfo<Score> info = new PageInfo<>(newList);
            HashMap<String, Object> map = new HashMap<>();
            map.put("scoreList", info);
            map.put("courseSet", courseSet);
            map.put("method", "course");
            map.put("value", courseName);
            map.put("teaId", teaId);
            model.addAttribute("map", map);

            return "teacher/studentList";
        }
    }

    @RequestMapping("/teacher/getScoreByName")
    public String getScoreByName(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("teaId") Integer teaId, @RequestParam("name") String stuName, Model model) {
        PageHelper.startPage(i, 12);
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        Set<String> courseSet = new HashSet<String>();
        List<Score> newList = new ArrayList<Score>();
        for (Score score : list) {
            courseSet.add(score.getCourse().getCourseName());
        }
        for (Score score : list) {
            if (Objects.equals(score.getStu().getUserName(), stuName)) {
                newList.add(score);
            }
        }
        PageInfo<Score> info = new PageInfo<>(newList);
        HashMap<String, Object> map = new HashMap<>();
        map.put("scoreList", info);
        map.put("courseSet", courseSet);
        map.put("method", "name");
        map.put("teaId", teaId);
        map.put("value", stuName);
        model.addAttribute("map", map);

        return "teacher/studentList";
    }

    @RequestMapping("/teacher/toAddScore")
    public String toAddScore(@RequestParam("id") Integer id, @RequestParam("teaId") Integer teaId, Model model) {
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        for (Score score : list) {
            if (Objects.equals(score.getId(), id)) {
                model.addAttribute("score", score);
            }
        }
        return "/teacher/scoreInfo";
    }

    @RequestMapping("/teacher/toEditScore")
    public String toEditScore(@RequestParam("id") Integer id, @RequestParam("teaId") Integer teaId, Model model) {
        List<Score> list = teacherServiceImpl.getScoreByTeaId(teaId);
        for (Score score : list) {
            if (Objects.equals(score.getId(), id)) {
                model.addAttribute("score", score);
            }
        }
        return "/teacher/scoreInfo";
    }

    @RequestMapping(value = "/teacher/score")
    public String editScore(@RequestParam("id") Integer id, Integer score, Integer teaId) {
        teacherServiceImpl.editScore(id, score);
        return "redirect:/teacher/scoreList?id=" + teaId;
    }

    @RequestMapping("/teacher/selectCourseList")
    public String selectCourse(@RequestParam(value = "page", defaultValue = "1") Integer i, @RequestParam("id") Integer teaId, Model model) {
        PageHelper.startPage(i, 12);//只需引用这个就可以分页 
        List<Course> list = teacherServiceImpl.getSelectCourseByTeaId(teaId);
        //将查询到的list放入PageInfo，可以获得所有信息
        PageInfo<Course> info = new PageInfo<>(list);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList", info);
        model.addAttribute("map", map);
        if (teaId == null) {
            return "login";
        }
        return "teacher/selectCourse";
    }

    @RequestMapping("/teacher/course/toAddSelectCourse")
    public String toAddSelectCourse(String teaNo, Model model) {
        model.addAttribute("teaNo", teaNo);
        return "teacher/selectSave";
    }

    @RequestMapping(value = "/teacher/selectCourse", method = RequestMethod.POST)
    public String addSelectCourse(String courseName, String courseType, Integer credit, String teaNo,Integer selectNum) {
        if (teacherServiceImpl.getTeaByTeaNo(teaNo) != null) {
            Course course = new Course(null, courseName, courseType, credit, teacherServiceImpl.getTeaByTeaNo(teaNo).get(0),null,selectNum);
            teacherServiceImpl.addSelectCourse(course);
            return "redirect:/teacher/selectCourseList?id=" + teacherServiceImpl.getTeaByTeaNo(teaNo).get(0).getId();
        } else {
            return "teacher/selectSave";
        }
    }

    @RequestMapping(value = "/teacher/selectCourse/{id}", method = RequestMethod.GET)
    public String toEditSelectCourse(@PathVariable("id") Integer id, Model model) {
        Course course = teacherServiceImpl.getSelectCourseById(id);
        model.addAttribute("course", course);
        return "teacher/selectEdit";
    }

    @RequestMapping(value = "/teacher/selectCourse/{id}", method = RequestMethod.PUT)
    public String editSelectCourse(@PathVariable("id") Integer id, String courseName, String courseType, Integer credit, String teaNo,Integer selectNum) {
        Course course = new Course(id, courseName, courseType, credit, teacherServiceImpl.getTeaByTeaNo(teaNo).get(0),null,selectNum);
        teacherServiceImpl.editSelectCourse(course);
        return "redirect:/teacher/selectCourseList?id=" + teacherServiceImpl.getTeaByTeaNo(teaNo).get(0).getId();
    }

    //@ResponseBody
    @RequestMapping(value = "/teacher/selectCourse/{id}", method = RequestMethod.DELETE)
    public String deleteSelectCourse(@PathVariable("id") Integer id, @RequestParam("teaId") Integer teaId) {
        teacherServiceImpl.deleteSelectCourse(id);
        return "redirect:/teacher/selectCourseList?id=" + teaId;
    }

    @RequestMapping("/teacher/courseInput/{id}")
    public String courseInput(@PathVariable("id") Integer id, Integer teaId) {
        teacherServiceImpl.courseInput(id, teaId);
        return "redirect:/teacher/selectCourseList?id=" + teaId;
    }
}
