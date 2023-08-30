package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private int id;
    private String name;
    private boolean active;
    private List<Grade> grades;

    public Course(int id, String name, boolean active, List<Grade> grades) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.grades = grades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}