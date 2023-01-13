package com.shahin.drivesafe.Models;

public class NotificationModel {

    String msg;
    long createat;

    public NotificationModel() {
    }

    public NotificationModel(String msg, long createat) {
        this.msg = msg;
        this.createat = createat;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCreateat() {
        return createat;
    }

    public void setCreateat(long createat) {
        this.createat = createat;
    }
}
