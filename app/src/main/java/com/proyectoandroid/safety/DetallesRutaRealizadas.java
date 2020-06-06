package com.proyectoandroid.safety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class DetallesRutaRealizadas extends AppCompatActivity implements View.OnClickListener {

    private Button btnruta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ruta);

        TextView ruta=(TextView) findViewById(R.id.txt_ruta);
        TextView fecha=(TextView)findViewById(R.id.txt_fecha);
        btnruta = (Button)findViewById(R.id.btntomaruta);

        btnruta.setOnClickListener(this);

        Intent intent =getIntent();
        Bundle b=intent.getExtras();

        if(b!=null){
            ruta.setText(b.getString("RUT"));
            fecha.setText(b.getString("FEC"));


        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btntomaruta){
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", 28.43242324,77.8977673);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
    }
}
