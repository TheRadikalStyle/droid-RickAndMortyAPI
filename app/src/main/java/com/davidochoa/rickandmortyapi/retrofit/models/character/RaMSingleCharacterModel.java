package com.davidochoa.rickandmortyapi.retrofit.models.character;

import com.davidochoa.rickandmortyapi.retrofit.models.RaMLocationModel;
import com.davidochoa.rickandmortyapi.retrofit.models.RaMOriginModel;

import java.util.List;

public class RaMSingleCharacterModel {
    public int id;
    public String name;
    public String status;
    public String species;
    public String type;
    public String gender;
    public RaMOriginModel origin;
    public RaMLocationModel location;
    public String image;
    public List<String> episode;
    public String url;
    public String created;
}
