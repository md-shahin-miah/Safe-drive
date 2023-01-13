package com.shahin.drivesafe.Models;

public class ReporterDataModel {

    String name, email,userid;

    public ReporterDataModel() {
    }

    public ReporterDataModel(String name, String email, String userid) {
        this.name = name;
        this.email = email;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
