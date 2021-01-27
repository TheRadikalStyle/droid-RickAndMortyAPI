package com.davidochoa.rickandmortyapi.retrofit.services;

import com.davidochoa.rickandmortyapi.retrofit.models.RaMResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RickAndMortyService {
    @GET("character")
    Call<RaMResponseModel> getAllCharacters();
}
