package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectoandroid.Adapter.FotosAdapter;
import com.proyectoandroid.Modelo.Fotos;

import java.util.ArrayList;
import java.util.List;

public class FotosActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FotosAdapter fotosAdapter;
    private ArrayList<Fotos> postLists;

    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);



        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recycle_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        fotosAdapter = new FotosAdapter(this,postLists);
        recyclerView.setAdapter(fotosAdapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Fotos_subidas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    postLists.removeAll(postLists);
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Fotos fotos = snapshot.getValue(Fotos.class);
                        postLists.add(fotos);
                    }

                    fotosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Fotos_subidas");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Fotos fotos = snapshot.getValue(Fotos.class);

                    //Aqui hay que cambiarrrrrrr
                    postLists.add(fotos);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}