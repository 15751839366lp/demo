/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.service;

import com.system.bean.Admin;
import com.system.bean.Student;
import com.system.bean.Teacher;
import com.system.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author asus
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public Student getStuByStuNo(String userNo, String password) {
        return userMapper.getStu(userNo, password);
    }
    
    @Override
    public Teacher getTeaByTeaNo(String userNo, String password) {
        return userMapper.getTea(userNo, password);
    }
    
    @Override
    public Admin getAdminByAdminNo(String userNo, String password) {
        return userMapper.getAdmin(userNo, password);
    }
}
