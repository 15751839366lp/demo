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
public class Admin {
    private Integer id;
    private String userName;
    private String password;
    private String adminNo;
    private String userType;

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", adminNo=" + adminNo + ", userType=" + userType + '}';
    }

    public Admin() {
    }

    public Admin(Integer id, String userName, String password, String adminNo, String userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.adminNo = adminNo;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(String adminNo) {
        this.adminNo = adminNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    
}
