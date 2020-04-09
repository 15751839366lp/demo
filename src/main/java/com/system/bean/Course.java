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
public class Course {
    private Integer id;
    private String courseName;
    private String courseType;
    private Integer credit;
    private Teacher tea;
    private Integer stuNum;
    private Integer selectNum;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", courseType='" + courseType + '\'' +
                ", credit=" + credit +
                ", tea=" + tea +
                ", stuNum=" + stuNum +
                ", selectNum=" + selectNum +
                '}';
    }

    public Integer getSelectNum() {
        return selectNum;
    }

    public void setSelectNum(Integer selectNum) {
        this.selectNum = selectNum;
    }

    public Integer getStuNum() {
        return stuNum;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Teacher getTea() {
        return tea;
    }

    public void setTea(Teacher tea) {
        this.tea = tea;
    }

    public Course(Integer id, String courseName, String courseType, Integer credit, Teacher tea, Integer stuNum, Integer selectNum) {
        this.id = id;
        this.courseName = courseName;
        this.courseType = courseType;
        this.credit = credit;
        this.tea = tea;
        this.stuNum = stuNum;
        this.selectNum = selectNum;
    }

    public Course() {
    }
    
}
