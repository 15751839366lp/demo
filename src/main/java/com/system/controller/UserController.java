/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.bean.Admin;
import com.system.bean.Student;
import com.system.bean.Teacher;
import com.system.service.UserServiceImpl;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author asus
 */
@SessionAttributes(value = "user", types = {Student.class,Admin.class,Teacher.class})
@Controller
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    ObjectMapper mapper = new ObjectMapper();

    @RequestMapping("/")
    public String first(){
        return "login";
    }
    
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public String getUser(Model model, String userNo, String password, String userType) throws Exception {
        if (Objects.equals(userType, "管理员")) {
            Admin user = userServiceImpl.getAdminByAdminNo(userNo, password);
            if (user == null) {
                return "redirect:/login.jsp";
            } else {
                model.addAttribute("user", user);
                return "redirect:/index.jsp";
            }
        } else if (Objects.equals(userType, "学生")) {
            Student user = userServiceImpl.getStuByStuNo(userNo, password);
            if (user == null) {
                return "redirect:/login.jsp";
            } else {
                model.addAttribute("user", user);
                return "redirect:/index.jsp";
            }
        } else if (Objects.equals(userType, "教师")) {
            Teacher user = userServiceImpl.getTeaByTeaNo(userNo, password);
            if (user == null) {
                return "redirect:/login.jsp";
            } else {
                model.addAttribute("user", user);
                return "redirect:/index.jsp";
            }
        } else {
            return "redirect:/login.jsp";
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login.jsp";
    }
}
