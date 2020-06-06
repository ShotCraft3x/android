package com.proyectoandroid.safety;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class RutasRealizadas extends AppCompatActivity {

    //Matriz
    ListView lista;
    //almacena los datos de los lugares
    String[][] datos={
            { "","Horario:13:00-21:00" ,"Thoros Bike" },
            { "","Horario:14:00-21:30" ,"Ciclos Acquea" },
            { "","Horario:16:00-15:20" ,"Cycles Serena" },
            { "","Horario:17:00-15:10" ,"Original Bike" },
    };
    //Obtiene  las imagenes de la carpeta drawable
    int[] datosImg={R.drawable.ruta3,R.drawable.ruta3,R.drawable.ruta3,R.drawable.ruta3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realizadas);
        //instanciar la lista
        lista=(ListView) findViewById(R.id.lvLista);

        lista.setAdapter(new AdapRutas(this,datos,datosImg));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalle=new Intent(view.getContext(), DetallesRutaRealizadas.class);
                visorDetalle.putExtra("FEC",datos[position][0]);
                visorDetalle.putExtra("HOR",datos[position][1]);

                visorDetalle.putExtra("RUT",datos[position][2]);




                startActivity(visorDetalle);
            }
        });
    }
}
