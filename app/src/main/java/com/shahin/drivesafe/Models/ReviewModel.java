package com.shahin.drivesafe.Models;

public class ReviewModel {

    String username,userid,review,driverid;
    float rating;
    long createat;

    public ReviewModel() {
    }


    public ReviewModel(String username, String userid, String review, String driverid, float rating, long createat) {
        this.username = username;
        this.userid = userid;
        this.review = review;
        this.driverid = driverid;
        this.rating = rating;
        this.createat = createat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getCreateat() {
        return createat;
    }

    public void setCreateat(long createat) {
        this.createat = createat;
    }
}
