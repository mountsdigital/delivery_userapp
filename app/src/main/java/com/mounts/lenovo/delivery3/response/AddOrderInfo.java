package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

public class AddOrderInfo {
    @SerializedName("isSuccess")
    public boolean isSuccess;
    @SerializedName("duration")
    public String duration;
    @SerializedName("cost")
    public int cost;


}
