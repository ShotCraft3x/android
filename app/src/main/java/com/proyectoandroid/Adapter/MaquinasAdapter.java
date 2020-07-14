package com.proyectoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.Modelo.MaquinasEjercicios;
import com.proyectoandroid.Modelo.Plazas;
import com.proyectoandroid.safety.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MaquinasAdapter extends RecyclerView.Adapter<MaquinasAdapter.MyViewHolder> implements View.OnClickListener{

    private Context context;

    private List<MaquinasEjercicios> apps;

    private View.OnClickListener listener;


    public MaquinasAdapter(Context context, List<MaquinasEjercicios> apps) {
        this.context = context;
        this.apps = apps;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Toast.makeText(context, "Llego aqui", Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_maquinas,parent,false);
        view.setOnClickListener(this);
        MyViewHolder holder = new MaquinasAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

       MaquinasEjercicios app = apps.get(position);
        holder.mName.setText(app.getNombre());
        holder.mdescrip.setText(app.getDescripcion());

        //cargar foto a la activity
        Picasso.with(context).load(app.getImagen()).into(holder.mImage, new Callback() {
            //Cuando la imagen se carga exitosamente
            @Override
            public void onSuccess() {
                //holder.progress.setVisibility(View.GONE);
                //Se hace visible la imagen
                holder.mImage.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError() {

                Toast.makeText(context,"Tienes un error", Toast.LENGTH_SHORT).show();

            }
        });


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
        TextView mName,mdescrip;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.txt);
            mdescrip = itemView.findViewById(R.id.sub_txt);
            mImage = itemView.findViewById(R.id.img);
        }
    }


}