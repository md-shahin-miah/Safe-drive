package com.shahin.drivesafe.Models;

public class CaseModel {
    String name;
    int point;

    public CaseModel(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public CaseModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}

