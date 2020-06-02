package com.proyectoandroid.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Descubre extends AppCompatActivity {

    //Matriz
    ListView lista;
    //almacena los datos de los lugares
    String[][] datos={
            { "Faro Monumental de La Serena" , "Dirección: Avda. Fco de Aguirre, La Serena, Coquimbo" ,"8", "El Faro Monumental de La Serena es un faro chileno ubicado en la Avenida del Mar de la ciudad de La Serena. La estructura se caracteriza por ser el símbolo de reconocimiento público de la ciudad, siendo uno de los lugares turísticos más representativos y concurridos de la zona." },
            { "Cruz del Tercer Milenio" , "Dirección: Tte Merino 32, Coquimbo" ,"9","La Cruz del Tercer Milenio es un monumento conmemorativo religioso ubicado en el cerro El Vigía de Coquimbo, Chile. Fue construido con el motivo del jubileo del año 2000 de la Iglesia católica. Su construcción fue iniciada en 1999 y terminada en 2001. Posee 83 metros de altura, 40 m de ancho y se encuentra a 210 msnm." } ,
            { "Museo Arqueológico de La Serena" , "Dirección: Cordovéz esquina, Cienfuegos, La Serena, Coquimbo","4", "El Museo Arqueológico de La Serena es un museo ubicado en el centro de la ciudad de La Serena, capital de la Región de Coquimbo. Fue creado el 3 de abril de 1943. Cinco años después fue transferido a la Dirección de Bibliotecas, Archivos y Museos." },
            { "Domo Cultura Ánimas", "Dirección: Aldunate S/N, Coquimbo","7", "El Domo Cultura Ánimas es un museo de sitio ubicado en la ciudad chilena de Coquimbo. Debe su nombre a la cultura Ánimas, precursora de la cultura diaguita que habitó el norte chico de Chile entre 900 y 1200 d.C., y de la cual se encontraron restos durante unas excavaciones en el sector." },


    };
    //Obtener  las imagenes de la carpeta drawable
    int[] datosImg={R.drawable.faro,R.drawable.cruz,R.drawable.museo,R.drawable.domo};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descubrir);
        //instanciar la lista
        lista=(ListView) findViewById(R.id.lvLista);

        lista.setAdapter(new Adaptador(this,datos,datosImg));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalle=new Intent(view.getContext(),DetallesLugar.class);
                visorDetalle.putExtra("TIT",datos[position][0]);
                visorDetalle.putExtra("DES",datos[position][3]);




                startActivity(visorDetalle);
            }
        });
    }
}
