package com.proyectoandroid.safety;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class DetallesRutaRealizadas extends AppCompatActivity implements View.OnClickListener {

    private Button btnruta;

    private double lat = 0.0;
    private double lng = 0.0;

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
            lat = b.getDouble("lat");
            lng = b.getDouble("lng");



        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btntomaruta){
            LatLng p1 = new LatLng(lat,lng);

            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", p1.latitude,p1.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        }
    }
}
