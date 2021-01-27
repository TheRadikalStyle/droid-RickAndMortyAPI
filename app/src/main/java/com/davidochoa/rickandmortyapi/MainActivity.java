package com.davidochoa.rickandmortyapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.davidochoa.rickandmortyapi.recycler.RecyclerAdapter;
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
    private RecyclerAdapter adapter;
    private LinearLayoutManager lLayoutManager;
    private LinearLayout loadingBox;
    private List<RaMResultsModel> resultadosIniciales;
    private int pagination = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBox = findViewById(R.id.main_activity_loadingbox);
        recyclerView = findViewById(R.id.main_recyclerview_recyclerview);
        recyclerView.setHasFixedSize(true);

        ShowLoadingBox();

        lLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                //Credits: https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
                if (dy > 0) {
                    if ((lLayoutManager.getChildCount() + lLayoutManager.findFirstVisibleItemPosition()) >= lLayoutManager.getItemCount()) {
                        GetItemPagination();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        GetCharacters();
    }

    private void ShowLoadingBox(){
        loadingBox.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void HideLoadingBox(){
        loadingBox.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
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
                CalculateIntPagination(response.body().info.next);
                resultadosIniciales = response.body().results;
                SetRecyclerView(resultadosIniciales);
                HideLoadingBox();
            }

            @Override
            public void onFailure(Call<RaMResponseModel> call, Throwable t) {
                HideLoadingBox();
                Toast.makeText(MainActivity.this, "Ocurrió un error al obtener la información", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SetRecyclerView(List<RaMResultsModel> resultados){
        adapter = new RecyclerAdapter(resultados, this);
        recyclerView.setAdapter(adapter);
    }

    private int CalculateIntPagination(String next) {
        String[] divURL = next.split("=");
        String pagID = divURL[divURL.length - 1];
        return pagination = Integer.parseInt(pagID);
    }

    private void GetItemPagination() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.rickandmorty_api))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RickAndMortyService service = retrofit.create(RickAndMortyService.class);
        Call<RaMResponseModel> characters = service.getAllCharactersPagination(pagination);
        characters.enqueue(new Callback<RaMResponseModel>() {
            @Override
            public void onResponse(Call<RaMResponseModel> call, Response<RaMResponseModel> response) {
                CalculateIntPagination(response.body().info.next);
                List<RaMResultsModel> nuevosResultados = response.body().results;

                resultadosIniciales.addAll(nuevosResultados);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<RaMResponseModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "No se pueden obtener más datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}