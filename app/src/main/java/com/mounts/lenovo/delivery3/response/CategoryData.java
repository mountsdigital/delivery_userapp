package com.mounts.lenovo.delivery3.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryData {
    @SerializedName("id")
    public int cateId;

    @SerializedName("name")
    public String cateName;

    @SerializedName("adds_on_services")
    public List<AddsOnServices> addsOnServices;
}
