package com.proyectoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.Modelo.Retos;
import com.proyectoandroid.safety.R;


import java.util.List;

public class RetosAdapter extends RecyclerView.Adapter<RetosAdapter.MyViewHolder> implements View.OnClickListener{

    private List<Retos> apps;

    private Context context;
    private View.OnClickListener listener;


    public RetosAdapter(Context context, List<Retos> apps) {
        this.context = context;
        this.apps = apps;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_descubre,parent,false);
        view.setOnClickListener(this);
        MyViewHolder holder = new RetosAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Retos app = apps.get(position);
        holder.mName.setText(app.getNombre());
        holder.mImage.setImageResource(R.drawable.objreto);

    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }

    }

    public void setOnClickListener(View.OnClickListener listener){

        this.listener = listener;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mImage = itemView.findViewById(R.id.image);
        }
    }


}