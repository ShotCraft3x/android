package com.proyectoandroid.safety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectoandroid.Adapter.DescubreAdapter;
import com.proyectoandroid.fragments.DescubreFragment;

import java.util.ArrayList;
import java.util.List;

public class Descubre extends AppCompatActivity {

    RecyclerView mList1,mList2;
    private Button button;
    private Button button2;
    List<DescubreFragment> appList ;
    List<DescubreFragment> appList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubre);

        button = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);

        mList1 = findViewById(R.id.list1);
        mList2 = findViewById(R.id.list2);
        appList = new ArrayList<>();

        appList.add(new DescubreFragment(R.drawable.objreto,"Reto kilometro","(Completa 3 kilometro en..)"));
        appList.add(new DescubreFragment(R.drawable.objreto,"Reto de Tiempo","(Compleeta 1 hrs en..)"));
        appList.add(new DescubreFragment(R.drawable.objreto,"Reto velocidad","(Midiendo la tiempo de ...)"));
        appList.add(new DescubreFragment(R.drawable.objreto,"Reto nose","(nose que colocar..)"));
        appList.add(new DescubreFragment(R.drawable.objreto,"Reto de altura","(saltar en la cama)"));
        appList.add(new DescubreFragment(R.drawable.objreto,"Reto de ejercicio","(Dormir)"));

        appList2 = new ArrayList<>();

        appList2.add(new DescubreFragment(R.drawable.plazamujer1,"Plaza de la mujer",""));
        appList2.add(new DescubreFragment(R.drawable.plaza,"Plaza del tofo",""));
        appList2.add(new DescubreFragment(R.drawable.plaza,"Plaza a dormir",""));
        appList2.add(new DescubreFragment(R.drawable.plaza,"Plaza X",""));
        appList2.add(new DescubreFragment(R.drawable.plaza,"Plaza 1",""));
        appList2.add(new DescubreFragment(R.drawable.plaza,"Plaza 2",""));

        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        manager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList1.setLayoutManager(manager1);

        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList2.setLayoutManager(manager2);

        DescubreAdapter adaptor1 = new DescubreAdapter(this,appList);
        mList1.setAdapter(adaptor1);

        DescubreAdapter adaptor2 = new DescubreAdapter(this,appList2);
        mList2.setAdapter(adaptor2);
    }
    public void onClick(View v) {
        if(v.getId() == R.id.button){
            Intent intent = new Intent(this.getApplicationContext(), DetalleReto.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.button2){
            Intent intent = new Intent(this.getApplicationContext(), PlazaDetalle.class);
            startActivity(intent);
        }

        }


}