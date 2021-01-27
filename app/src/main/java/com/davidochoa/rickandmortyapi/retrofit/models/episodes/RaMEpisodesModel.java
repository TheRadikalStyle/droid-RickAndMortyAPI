package com.davidochoa.rickandmortyapi.retrofit.models.episodes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RaMEpisodesModel {
    public int id;
    public String name;
    public String air_date;
    public String episode;
    public List<String> characters;
    public String url;
    public String created;
}
