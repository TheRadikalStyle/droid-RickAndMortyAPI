package com.davidochoa.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.davidochoa.rickandmortyapi.recycler.RecyclerAdapter;
import com.davidochoa.rickandmortyapi.retrofit.models.RaMInfoModel;
import com.davidochoa.rickandmortyapi.retrofit.models.RaMResponseModel;
import com.davidochoa.rickandmortyapi.retrofit.models.RaMResultsModel;
import com.davidochoa.rickandmortyapi.retrofit.services.RickAndMortyService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recyclerview_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetCharacters();
    }


    private void GetCharacters(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.rickandmorty_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RickAndMortyService service = retrofit.create(RickAndMortyService.class);
        Call<RaMResponseModel> characters = service.getAllCharacters();

        characters.enqueue(new Callback<RaMResponseModel>() {
            @Override
            public void onResponse(Call<RaMResponseModel> call, Response<RaMResponseModel> response) {
                //Log.d("Exito", String.valueOf(response.body().info.count));
                //Log.d("Exito", String.valueOf(response.body().results.size()));

                List<RaMResultsModel> resultados = response.body().results;
                SetRecyclerView(resultados);
                /*for (RaMResultsModel datos: resultados) {
                    Log.d("personaje -> ", datos.name);
                }*/
            }

            @Override
            public void onFailure(Call<RaMResponseModel> call, Throwable t) {
                Log.d("Error" , t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private void SetRecyclerView(List<RaMResultsModel> resultados){
        RecyclerAdapter adapter = new RecyclerAdapter(resultados, this);
        recyclerView.setAdapter(adapter);
    }
}