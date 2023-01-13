package com.shahin.drivesafe.Models;

import java.io.Serializable;

public class UserModel implements Serializable {

    String name,nid,license,address,phone,owner_name,owner_address,owner_phone,number_plate,email,password,image,qrcode,birthdate,gender;
    long create_at,last_open;
    int point;
    String userId;
    float avgrating;


    public UserModel() {
    }


    public UserModel(String name, String nid, String license, String address, String phone, String owner_name, String owner_address, String owner_phone, String number_plate, String email, String password, String image, String qrcode, String birthdate, String gender, long create_at, long last_open, int point, String userId, float avgrating) {
        this.name = name;
        this.nid = nid;
        this.license = license;
        this.address = address;
        this.phone = phone;
        this.owner_name = owner_name;
        this.owner_address = owner_address;
        this.owner_phone = owner_phone;
        this.number_plate = number_plate;
        this.email = email;
        this.password = password;
        this.image = image;
        this.qrcode = qrcode;
        this.birthdate = birthdate;
        this.gender = gender;
        this.create_at = create_at;
        this.last_open = last_open;
        this.point = point;
        this.userId = userId;
        this.avgrating = avgrating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_address() {
        return owner_address;
    }

    public void setOwner_address(String owner_address) {
        this.owner_address = owner_address;
    }

    public String getOwner_phone() {
        return owner_phone;
    }

    public void setOwner_phone(String owner_phone) {
        this.owner_phone = owner_phone;
    }

    public String getNumber_plate() {
        return number_plate;
    }

    public void setNumber_plate(String number_plate) {
        this.number_plate = number_plate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCreate_at() {
        return create_at;
    }

    public void setCreate_at(long create_at) {
        this.create_at = create_at;
    }

    public long getLast_open() {
        return last_open;
    }

    public void setLast_open(long last_open) {
        this.last_open = last_open;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getAvgrating() {
        return avgrating;
    }

    public void setAvgrating(float avgrating) {
        this.avgrating = avgrating;
    }
}

