package com.example.testdb;

import androidx.annotation.NonNull;

public class Student {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public Student(String name) {
        this.id = id;
        this.name = name;
    }


    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
