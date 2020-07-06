package com.proyectoandroid.safety;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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

    public void startCronometro(){

        miServicioNotificacion.isOn = true;
        miServicioNotificacion.corriendo = true;


        Toast.makeText(getApplicationContext(),"Tiempo iniciado",Toast.LENGTH_SHORT).show();
        Intent intentService = new Intent(this,miServicioNotificacion.class);
        startService(intentService);


    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btntomaruta){
            startCronometro();

            LatLng p1 = new LatLng(lat,lng);
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", p1.latitude,p1.longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);



        }
    }


}
