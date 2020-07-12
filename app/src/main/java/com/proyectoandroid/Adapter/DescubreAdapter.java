package com.proyectoandroid.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.safety.R;
import com.proyectoandroid.fragments.DescubreFragment;


import java.util.List;

public class DescubreAdapter extends RecyclerView.Adapter<DescubreAdapter.MyViewHolder> {

    private Context context;

    private List<DescubreFragment> apps;


    public DescubreAdapter(Context context, List<DescubreFragment> apps) {
        this.context = context;
        this.apps = apps;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName,mdescrip;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mdescrip = itemView.findViewById(R.id.descripcion);
            mImage = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_descubre,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DescubreFragment app = apps.get(position);

        holder.mName.setText(app.getName());
        holder.mdescrip.setText(app.getDescrip());
        holder.mImage.setImageResource(app.getImage());


    }

    @Override
    public int getItemCount() {
        return apps.size();
    }





}