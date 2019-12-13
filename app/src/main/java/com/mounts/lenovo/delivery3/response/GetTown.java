package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTown {

    @SerializedName("towns")
    public List<ReceiverCity> towns;
    @SerializedName("towns")
    public List<ReceiverCity> getTowns(){
        return towns;
    }
}
