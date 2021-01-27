package com.davidochoa.rickandmortyapi.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaMResponseModel {
    @SerializedName("info")
    @Expose
    public RaMInfoModel info;

    @SerializedName("results")
    @Expose
    public List<RaMResultsModel> results;
}
