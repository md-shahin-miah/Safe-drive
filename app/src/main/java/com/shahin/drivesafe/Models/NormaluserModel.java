package com.shahin.drivesafe.Models;

public class NormaluserModel {

    String username,email,userid;

    public NormaluserModel() {
    }

    public NormaluserModel(String username, String email, String userid) {
        this.username = username;
        this.email = email;
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
