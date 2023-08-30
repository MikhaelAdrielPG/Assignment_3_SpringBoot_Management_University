package com.example.demo.model;

public class Grade {
    private String type; // misal : quiz atau exam
    private int value;

    public Grade(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}