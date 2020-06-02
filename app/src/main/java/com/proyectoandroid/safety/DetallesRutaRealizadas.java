package com.proyectoandroid.safety;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetallesRutaRealizadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ruta);

        TextView ruta=(TextView) findViewById(R.id.txt_ruta);
        TextView fecha=(TextView)findViewById(R.id.txt_fecha);


        Intent intent =getIntent();
        Bundle b=intent.getExtras();

        if(b!=null){
            ruta.setText(b.getString("RUT"));
            fecha.setText(b.getString("FEC"));


        }
    }
}
