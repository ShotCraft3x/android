package com.proyectoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.proyectoandroid.Modelo.Fotos;
import com.proyectoandroid.Modelo.Usuario;
import com.proyectoandroid.safety.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FotosAdapter extends RecyclerView.Adapter<FotosAdapter.ViewHolder>{

    public List<Fotos> galerialist;
    Context context;

    private String userid;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;


    private FirebaseUser firebaseUser;

    public FotosAdapter(Context mContext, List<Fotos> mPost) {
        this.context = mContext;
        this.galerialist = mPost;

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_fotos,parent,false);


        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        //Aqui hay algo
        final Fotos fotos = galerialist.get(position);

        holder.tv_titulo.setText(fotos.getTitulo());
        holder.tv_descripcion.setText(fotos.getDescripcion());
        //cargar foto a la activity
        Picasso.with(context).load(fotos.getFoto()).into(holder.img_foto, new Callback() {
            //Cuando la imagen se carga exitosamente
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
                //Se hace visible la imagen
                holder.img_foto.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError() {

                Toast.makeText(context,"Tienes un error", Toast.LENGTH_SHORT).show();

            }
        });

        userid = mAuth.getCurrentUser().getUid();

        isLikes(fotos.getIdfoto(),holder.img_like);
        //Toast.makeText(context, "ID: "+ userid, Toast.LENGTH_SHORT).show();
        nrLikes(holder.tv_likes,fotos.getIdfoto());
        holder.img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.img_like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(fotos.getIdfoto())
                            .child(userid).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(fotos.getIdfoto())
                            .child(userid).removeValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return galerialist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Declaracion de variables en el view holder
        public ImageView img_foto;
        public ImageView img_like;
        public TextView tv_titulo;
        public TextView tv_likes;
        public TextView tv_descripcion;
        ProgressBar progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //Referencia de variables
            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            img_foto = itemView.findViewById(R.id.img_foto);
            progress = itemView.findViewById(R.id.progress_bar_galeria);
            tv_descripcion = itemView.findViewById(R.id.description);
            img_like = itemView.findViewById(R.id.img_like);
            tv_likes = itemView.findViewById(R.id.tv_likes);


        }
    }

    private void isLikes(String postid, final ImageView imageView){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");


                }else{
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void nrLikes(final TextView likes, String postid){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+" Likes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
