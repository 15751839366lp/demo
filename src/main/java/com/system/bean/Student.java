package com.system.bean;

import java.io.Serializable;

public class Student implements Serializable {

    private Integer id;
    private String userName;
    private String password;
    private String stuNo;
    private String professional;
    private String userType;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", stuNo='" + stuNo + '\'' +
                ", professional='" + professional + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public Student(Integer id, String userName, String password, String stuNo, String professional, String userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.stuNo = stuNo;
        this.professional = professional;
        this.userType = userType;
    }
    
    
    public Student() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

}
