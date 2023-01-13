package com.shahin.drivesafe.Robi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {


    @SerializedName("is_there")
    @Expose
    Boolean stat;

    public Boolean getStat() {
        return stat;
    }

    public void setStat(Boolean stat) {
        this.stat = stat;
    }
}
