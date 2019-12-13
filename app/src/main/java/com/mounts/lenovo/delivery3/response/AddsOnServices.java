package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

public class AddsOnServices {
    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    public String logo;

    @SerializedName("opening")
    public String opening;

    @SerializedName("closing")
    public String closing;

    @SerializedName("address")
    public String address;

    @SerializedName("lat")
    public String lat;

    @SerializedName("lon")
    public String lon;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;

    @SerializedName("category_id")
    public int categoryId;
}
