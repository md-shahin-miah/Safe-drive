package com.shahin.drivesafe.Models;

public class ReportModel {

    String driver_name,phone,userid,description,location,reportername,repoterid,repoid;
    long createat;
    int lostPoint;
    String driverimage;

    public ReportModel() {
    }

    public ReportModel(String driver_name, String phone, String userid, String description, String location, String reportername, String repoterid, String repoid, long createat, int lostPoint, String driverimage) {
        this.driver_name = driver_name;
        this.phone = phone;
        this.userid = userid;
        this.description = description;
        this.location = location;
        this.reportername = reportername;
        this.repoterid = repoterid;
        this.repoid = repoid;
        this.createat = createat;
        this.lostPoint = lostPoint;
        this.driverimage = driverimage;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportername() {
        return reportername;
    }

    public void setReportername(String reportername) {
        this.reportername = reportername;
    }

    public String getRepoterid() {
        return repoterid;
    }

    public void setRepoterid(String repoterid) {
        this.repoterid = repoterid;
    }

    public String getRepoid() {
        return repoid;
    }

    public void setRepoid(String repoid) {
        this.repoid = repoid;
    }

    public long getCreateat() {
        return createat;
    }

    public void setCreateat(long createat) {
        this.createat = createat;
    }

    public int getLostPoint() {
        return lostPoint;
    }

    public void setLostPoint(int lostPoint) {
        this.lostPoint = lostPoint;
    }

    public String getDriverimage() {
        return driverimage;
    }

    public void setDriverimage(String driverimage) {
        this.driverimage = driverimage;
    }
}
