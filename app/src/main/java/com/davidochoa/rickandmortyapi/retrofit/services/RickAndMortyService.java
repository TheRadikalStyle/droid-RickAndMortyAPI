package com.davidochoa.rickandmortyapi.retrofit.services;

import com.davidochoa.rickandmortyapi.retrofit.models.RaMResponseModel;
import com.davidochoa.rickandmortyapi.retrofit.models.character.RaMSingleCharacterModel;
import com.davidochoa.rickandmortyapi.retrofit.models.episodes.RaMEpisodesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RickAndMortyService {
    @GET("character")
    Call<RaMResponseModel> getAllCharacters();

    @GET("character/{id}")
    Call<RaMSingleCharacterModel> getCharacterDetails(@Path("id") int id);

    @GET("episode/{id}")
    Call<RaMEpisodesModel> getEpisodeDetails(@Path("id") int id);

    @GET("character/")
    Call<RaMResponseModel> getAllCharactersPagination(@Query("page") int page);
}
