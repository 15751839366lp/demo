/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.system.bean;

/**
 *
 * @author asus
 */
public class Teacher {
    private Integer id; 
    private String userName;
    private String password; 
    private String teaNo;
    private String userType;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", teaNo='" + teaNo + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public Teacher() {
    }

    public Teacher(Integer id, String userName, String password, String teaNo, String userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.teaNo = teaNo;
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTeaNo() {
        return teaNo;
    }

    public void setTeaNo(String teaNo) {
        this.teaNo = teaNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
}
