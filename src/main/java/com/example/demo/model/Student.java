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

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // validate student name
    public boolean isValidName(String name) {
        // Define a regex pattern to allow only letters and spaces
        /*
        ^           : Menandakan awal dari string.
        [a-zA-Z\\s] : Cocok dengan karakter alfabet (baik huruf besar A-Z maupun kecil a-z) dan spasi (\\s).
        +           : Menandakan bahwa pola sebelumnya (yaitu [a-zA-Z\\s]) dapat muncul satu atau lebih kali.
        $           : Menandakan akhir dari string.
        * */
        String pattern = "^[a-zA-Z\\s]+$";

        return name.matches(pattern);
    }
}