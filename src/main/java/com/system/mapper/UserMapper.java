/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.mapper;

import com.system.bean.Admin;
import com.system.bean.Student;
import com.system.bean.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author asus
 */
@Mapper
@Repository
public interface UserMapper {

    Student getStu(@Param("stuNo") String userNo, @Param("password") String password);

    Teacher getTea(@Param("teaNo") String userNo, @Param("password") String password);

    Admin getAdmin(@Param("adminNo") String userNo, @Param("password") String password);
}
