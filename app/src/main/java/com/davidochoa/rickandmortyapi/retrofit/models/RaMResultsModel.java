package com.davidochoa.rickandmortyapi.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaMResultsModel {
    @SerializedName("id")
    @Expose
    public Integer id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("species")
    @Expose
    public String species;

    @SerializedName("type")
    @Expose
    public String type;

    @SerializedName("gender")
    @Expose
    public String gender;

    @SerializedName("origin")
    @Expose
    public RaMOriginModel origin;

    @SerializedName("location")
    @Expose
    public RaMLocationModel location;

    @SerializedName("image")
    @Expose
    public String image;

    @SerializedName("episode")
    @Expose
    public List<String> episode;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("created")
    @Expose
    public String created;
}
