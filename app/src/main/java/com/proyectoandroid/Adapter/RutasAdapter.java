package com.proyectoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyectoandroid.Modelo.Rutas;
import com.proyectoandroid.safety.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RutasAdapter extends RecyclerView.Adapter<RutasAdapter.ViewHolder>
        implements View.OnClickListener{

    public List<Rutas> galerialist;
    Context context;
    private View.OnClickListener listener;


    public RutasAdapter(Context mContext, List<Rutas> mPost) {
        this.context = mContext;
        this.galerialist = mPost;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ruta,parent,false);


        view.setOnClickListener(this);

        ViewHolder holder = new RutasAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Aqui hay algo
        final Rutas rutas = galerialist.get(position);

        holder.txtnombre.setText(rutas.getNombre());
        holder.txtdescripcion.setText(rutas.getDescripcion());

        //cargar foto a la activity
        Picasso.with(context).load(rutas.getImagen()).into(holder.img_foto, new Callback() {
            //Cuando la imagen se carga exitosamente
            @Override
            public void onSuccess() {
                //holder.progress.setVisibility(View.GONE);
                //Se hace visible la imagen
                holder.img_foto.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError() {

                Toast.makeText(context,"Tienes un error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return galerialist.size();
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


    public class ViewHolder extends RecyclerView.ViewHolder{

        //Declaracion de variables en el view holder
        public ImageView img_foto;
        public TextView txtnombre;
        public TextView txtdescripcion;
        //ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Referencia de variables
            txtnombre = itemView.findViewById(R.id.txt_nombre);
            txtdescripcion = itemView.findViewById(R.id.txt_descripcion);
            //progress = itemView.findViewById(R.id.progress_bar_galeria);
            img_foto = itemView.findViewById(R.id.ivImagen);


        }
    }


}
