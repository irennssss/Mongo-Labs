package com.example.demo.persistence;

import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String fullName;
    private String position;

    private List<Duty> duties = new ArrayList<>();

    public Employee() {}

    public Employee(String fullName, String position) {
        this.fullName = fullName;
        this.position = position;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public List<Duty> getDuties() { return duties; }
    public void setDuties(List<Duty> duties) { this.duties = duties; }
}