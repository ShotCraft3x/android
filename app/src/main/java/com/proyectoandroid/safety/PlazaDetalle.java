package com.proyectoandroid.safety;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.adapters.PlazaDetAdapter;
import com.proyectoandroid.fragments.PlazaDetFragment;

import java.util.ArrayList;
import java.util.List;

public class PlazaDetalle extends AppCompatActivity {

    RecyclerView mList1;
    List<PlazaDetFragment> appList ;
    private Button bntUbic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_plaza);

        bntUbic = (Button)findViewById(R.id.btnUbicacion);


        mList1 = findViewById(R.id.listplaza);

        appList = new ArrayList<>();

        appList.add(new PlazaDetFragment(R.drawable.caminador_aereo,"Caminador aereo","Ejercicio para piernas"));
        appList.add(new PlazaDetFragment(R.drawable.elipticachi1,"Simulador de ski simple","Ejercicio para piernas"));
        appList.add(new PlazaDetFragment(R.drawable.caminador_aereo,"Ejemplo","descripcion"));
        appList.add(new PlazaDetFragment(R.drawable.caminador_aereo,"Ejemplo","descripcion"));



        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        mList1.setLayoutManager(manager1);

        PlazaDetAdapter adaptor1 = new PlazaDetAdapter(this,appList);
        mList1.setAdapter(adaptor1);


    }
    public void onClick(View v) {
        if(v.getId() == R.id.btnUbicacion){
            Intent intent = new Intent(this.getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }



    }



}