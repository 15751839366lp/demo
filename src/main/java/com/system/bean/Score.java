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
public class Score {
    private Integer id;  
    private Student stu;
    private Course course;
    private Integer score;

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", stu=" + stu +
                ", course=" + course +
                ", score=" + score +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStu() {
        return stu;
    }

    public void setStu(Student stu) {
        this.stu = stu;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Score(Integer id, Student stu, Teacher tea, Course course, Integer score) {
        this.id = id;
        this.stu = stu;
        this.course = course;
        this.score = score;
    }


    public Score() {
    }

}
