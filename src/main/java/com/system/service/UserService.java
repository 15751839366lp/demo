package com.system.service;

import com.system.bean.Admin;
import com.system.bean.Student;
import com.system.bean.Teacher;

public interface UserService {

    Student getStuByStuNo(String userNo, String password);

    Teacher getTeaByTeaNo(String userNo, String password);

    Admin getAdminByAdminNo(String userNo, String password);

}
