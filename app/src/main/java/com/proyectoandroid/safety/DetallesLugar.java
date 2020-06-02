package com.proyectoandroid.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetallesLugar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_lugar);

        TextView titulo=(TextView) findViewById(R.id.txt_fecha);
        TextView descripcion=(TextView)findViewById(R.id.txt_descripcion);


        Intent intent =getIntent();
        Bundle b=intent.getExtras();

        if(b!=null){
            titulo.setText(b.getString("TIT"));
            descripcion.setText(b.getString("DES"));


        }
    }
}