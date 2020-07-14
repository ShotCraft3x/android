package com.proyectoandroid.safety;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.Adapter.MaquinasAdapter;
import com.proyectoandroid.Adapter.PlazaDetAdapter;
import com.proyectoandroid.Modelo.MaquinasEjercicios;
import com.proyectoandroid.Modelo.Plazas;
import com.proyectoandroid.Modelo.Rutas;
import com.proyectoandroid.fragments.PlazaDetFragment;

import java.util.ArrayList;
import java.util.List;

public class PlazaDetalle extends AppCompatActivity {

    RecyclerView mList1;
    ArrayList<MaquinasEjercicios> listaMaquinas;
    private Button bntUbic;

    private MaquinasAdapter adaptermaquinas;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plaza);

        bntUbic = (Button)findViewById(R.id.btnUbicacion);
        mList1 = findViewById(R.id.listplaza);

        //Insercion de plazas
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        mList1.setHasFixedSize(true);
        mList1.setLayoutManager(linearLayoutManager);
        listaMaquinas = new ArrayList<>();

        adaptermaquinas = new MaquinasAdapter(this,listaMaquinas);
        mList1.setAdapter(adaptermaquinas);


        //listaMaquinas.removeAll(listaMaquinas);
        listaMaquinas= (ArrayList<MaquinasEjercicios>) getIntent().getSerializableExtra("miLista");
        adaptermaquinas.notifyDataSetChanged();





    }


    public void onClick(View v) {
        if(v.getId() == R.id.btnUbicacion){
            Intent intent = new Intent(this.getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }
    }



}