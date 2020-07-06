package com.proyectoandroid.safety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.proyectoandroid.Adapter.Adaptador;

public class Estadistica extends AppCompatActivity {

    //Matriz
    ListView lista;
    //almacena los datos de los lugares
    String[][] datos={
            {"Fecha:20-05.2020" , "Distancia recorrida:40km" ,"40", "Pasos:3000" },
            {"Fecha:20-05.2020" , "Distancia recorrida:40km" ,"40", "Pasos:3000" },
            {"Fecha:20-05.2020" , "Distancia recorrida:40km" ,"40", "Pasos:3000" },
            {"Fecha:20-05.2020" , "Distancia recorrida:40km" ,"40", "Pasos:3000" },

    };
    //Obtener  las imagenes de la carpeta drawable
    int[] datosImg={R.drawable.faro,R.drawable.cruz,R.drawable.museo,R.drawable.domo};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica);
        //instanciar la lista
        lista=(ListView) findViewById(R.id.lvLista);

        lista.setAdapter(new Adaptador(this,datos,datosImg));

        /*lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalle=new Intent(view.getContext(),DetallesLugar.class);
                visorDetalle.putExtra("TIT",datos[position][0]);
                visorDetalle.putExtra("DES",datos[position][3]);




                startActivity(visorDetalle);
            }
        });*/
    }
}
