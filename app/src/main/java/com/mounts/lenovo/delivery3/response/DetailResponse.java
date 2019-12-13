package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

public class DetailResponse {
    @SerializedName("isSuccess")
    public Boolean isSuccess;

    @SerializedName("email")
    public String email;

    @SerializedName("full_name")
    public String full_name;

    @SerializedName("phone_number")
    public String phone_number;

    @SerializedName("address")
    public String address;

    @SerializedName("profile_image")
    public String profile_image;
}
