package com.davidochoa.rickandmortyapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davidochoa.rickandmortyapi.retrofit.models.character.RaMSingleCharacterModel;
import com.davidochoa.rickandmortyapi.retrofit.models.episodes.RaMEpisodesModel;
import com.davidochoa.rickandmortyapi.retrofit.services.RickAndMortyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity {
    LinearLayout loadingBox, contentLayout;
    TextView nameTXV, statusTXV, speciesTXV, typeTXV, genderTXV, originTXV, locationTXV, fEpisodeTXV, fEpisodeAirDateTXV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        loadingBox = findViewById(R.id.details_activity_linearlayout_loadingbox);
        contentLayout = findViewById(R.id.details_activity_content);

        ShowLoadingBox();

        if(getIntent().getExtras() != null){
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            int id = Integer.parseInt(getIntent().getExtras().getString("urlCharacter"));

            nameTXV = findViewById(R.id.details_activity_name);
            statusTXV = findViewById(R.id.details_activity_status);
            speciesTXV = findViewById(R.id.details_activity_species);
            typeTXV = findViewById(R.id.details_activity_type);
            genderTXV = findViewById(R.id.details_activity_gender);
            originTXV = findViewById(R.id.details_activity_origin);
            locationTXV = findViewById(R.id.details_activity_location);
            fEpisodeTXV = findViewById(R.id.details_activity_firstepisode);
            fEpisodeAirDateTXV = findViewById(R.id.details_activity_firstepisode_airdate);

            GetData(id);
        }else{
            InitError();
        }
    }

    private void GetData(int id){
        Retrofit retrofit = GetRetrofitBuilder(getResources().getString(R.string.rickandmorty_api));

        RickAndMortyService service = retrofit.create(RickAndMortyService.class);
        Call<RaMSingleCharacterModel> characters = service.getCharacterDetails(id);

        characters.enqueue(new Callback<RaMSingleCharacterModel>() {
            @Override
            public void onResponse(Call<RaMSingleCharacterModel> call, Response<RaMSingleCharacterModel> response) {
                HideLoadingBox();
                if(response.body() != null){
                    ConfigUIElements(response.body());
                    GetEpisodeData(GetFirstEpisode(response.body().episode));
                }else {
                    InitError();
                }
            }

            @Override
            public void onFailure(Call<RaMSingleCharacterModel> call, Throwable t) {
                HideLoadingBox();
                InitError();
            }
        });
    }

    private String GetFirstEpisode(List<String> episode) {
        String firstEpisodeUrl = episode.get(0);
        return firstEpisodeUrl;
    }

    private void GetEpisodeData(String url){
        String[] divURL = url.split("/");
        String charID = divURL[divURL.length - 1];

        Retrofit retrofit = GetRetrofitBuilder(getResources().getString(R.string.rickandmorty_api));

        RickAndMortyService service = retrofit.create(RickAndMortyService.class);
        service.getEpisodeDetails(Integer.parseInt(charID)).enqueue(new Callback<RaMEpisodesModel>() {
            @Override
            public void onResponse(Call<RaMEpisodesModel> call, Response<RaMEpisodesModel> response) {
                UpdateFirstEpisodeElement(response.body().name, response.body().air_date);
            }

            @Override
            public void onFailure(Call<RaMEpisodesModel> call, Throwable t) {
                UpdateFirstEpisodeElement("Error - N/D", "Error - N/D");
            }
        });
    }

    private void ShowLoadingBox(){
        loadingBox.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
    }

    private void HideLoadingBox(){
        loadingBox.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }

    private void ConfigUIElements(RaMSingleCharacterModel data){
        nameTXV.setText(data.name);
        statusTXV.setText(data.status);
        speciesTXV.setText(data.species);
        typeTXV.setText(data.type);
        genderTXV.setText(data.gender);
        originTXV.setText(data.origin.name);
        locationTXV.setText(data.location.name);
        fEpisodeTXV.setText(getResources().getString(R.string.loading));
    }

    private void UpdateFirstEpisodeElement(String name, String airdate){
        fEpisodeTXV.setTextColor(getResources().getColor(R.color.material_red_900));
        fEpisodeAirDateTXV.setTextColor(getResources().getColor(R.color.material_red_900));

        fEpisodeTXV.setText(name);
        fEpisodeAirDateTXV.setText(airdate);
    }

    private Retrofit GetRetrofitBuilder(String baseURL){
        return new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void InitError(){
        Toast.makeText(DetailsActivity.this, getResources().getString(R.string.error_get_data), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}