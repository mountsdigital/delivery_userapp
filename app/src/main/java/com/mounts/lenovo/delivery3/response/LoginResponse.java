package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("isSuccess")
    public Boolean isSuccess;

    @SerializedName("sessionId")
    public String sessionId;

    @SerializedName("isCompleteProfile")
    public String isCompleteProfile;

    @SerializedName("error_message")
    public String error_message;

}
