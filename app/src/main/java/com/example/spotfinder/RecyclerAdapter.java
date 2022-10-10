package com.example.spotfinder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<FavouriteLandmarks> favLandmarks;



    public RecyclerAdapter(ArrayList<FavouriteLandmarks> favLandmarks){
        this.favLandmarks= favLandmarks;


    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{
        private TextView landmarktxt;

        public MyViewHolder(final View view){
            super(view);
            landmarktxt=view.findViewById(R.id.txt_landmark_name);
        }


    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String landmark = favLandmarks.get(position).getLandmark();
        holder.landmarktxt.setText(landmark);

    }


    @Override
    public int getItemCount() {
        return favLandmarks.size();
    }
}
