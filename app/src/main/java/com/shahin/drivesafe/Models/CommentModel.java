package com.shahin.drivesafe.Models;

public class CommentModel {

    String username,comment;
    long createAt;


    public CommentModel() {
    }

    public CommentModel(String username, String comment, long createAt) {
        this.username = username;
        this.comment = comment;
        this.createAt = createAt;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }


}
