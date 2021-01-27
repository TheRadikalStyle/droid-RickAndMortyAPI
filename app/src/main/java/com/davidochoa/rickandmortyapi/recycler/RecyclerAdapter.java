package com.davidochoa.rickandmortyapi.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.davidochoa.rickandmortyapi.R;
import com.davidochoa.rickandmortyapi.retrofit.models.RaMResultsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CharacterViewHolder> {
    private List<RaMResultsModel> results;
    private Context context;

    public RecyclerAdapter(List<RaMResultsModel> resultados, Context context){
        this.results = resultados;
        this.context = context;
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        TextView nameTXV, locationTXV, statusTXV;
        ImageView statusIMV, imagenPersonaje;

        CharacterViewHolder(View itemView) {
            super(itemView);
            nameTXV = itemView.findViewById(R.id.main_recyclerview_template_textview_name);
            statusTXV = itemView.findViewById(R.id.main_recyclerview_textview_status);
            statusIMV = itemView.findViewById(R.id.main_recyclerview_imageview_status);
            locationTXV = itemView.findViewById(R.id.main_recyclerview_textview_location);
            imagenPersonaje = itemView.findViewById(R.id.main_recyclerview_template_imageview_image);
        }
    }


    @NonNull
    @Override
    public RecyclerAdapter.CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recyclerview_template, parent, false);
        CharacterViewHolder pvh = new CharacterViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.CharacterViewHolder holder, int position) {
        holder.nameTXV.setText(results.get(position).name);
        holder.locationTXV.setText(results.get(position).location.name);
        holder.statusTXV.setText(results.get(position).status);

        switch (results.get(position).status){
            case "Alive":
                holder.statusIMV.setImageResource(R.drawable.status_green);
                break;
            case "Dead":
                holder.statusIMV.setImageResource(R.drawable.status_red);
                break;
           default:
                holder.statusIMV.setImageResource(R.drawable.status_grey);
                break;
        }

        Picasso.get().load(results.get(position).image).into(holder.imagenPersonaje);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Presionaste ->" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return results.size();
    }
}
