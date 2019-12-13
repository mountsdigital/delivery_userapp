package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

public class ReceiverCity {

    @SerializedName("name")
    public String name;
    @SerializedName("id")
    public String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
