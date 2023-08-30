package com.example.demo.model;

import java.util.List;

public class Student {
    private int id;
    private String name;
    private String major;
    private boolean active;
    private List<Course> courses;

    public Student(int id, String name, String major, boolean active, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.major = major;
        this.courses = courses;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Course> getCourses() {
        return courses;
    }
}