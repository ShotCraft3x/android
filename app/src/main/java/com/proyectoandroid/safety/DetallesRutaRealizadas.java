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
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.model.LatLng;
import com.proyectoandroid.Adapter.DetalleRutaAdapter;

import java.util.Locale;
import java.util.Map;

public class DetallesRutaRealizadas extends AppCompatActivity implements View.OnClickListener {

    private Button btnruta;
    private Button btnrutasafety;

    private double lat = 0.0;
    private double lng = 0.0;


    //Iniciar una nueva ruta

    private double latruta = 0.0;
    private double lngruta = 0.0;
    private String nombrepunto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ruta);

        TextView ruta=(TextView) findViewById(R.id.txt_ruta);
        TextView fecha=(TextView)findViewById(R.id.txt_fecha);
        btnruta = (Button)findViewById(R.id.btntomaruta);
        btnrutasafety = (Button)findViewById(R.id.button2);

        btnruta.setOnClickListener(this);
        btnrutasafety.setOnClickListener(this);

        Intent intent =getIntent();
        Bundle b=intent.getExtras();



        if(b!=null) {
            latruta = Double.valueOf(b.getString("lat"));
            lngruta = Double.valueOf(b.getString("lng"));
            nombrepunto = b.getString("name");

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


        if(view.getId()==R.id.button2){
            MapsActivity.isOn = true;
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            intent.putExtra("op",1);
            intent.putExtra("lat",latruta);
            intent.putExtra("lng",lngruta);
            intent.putExtra("name",nombrepunto);
            startActivity(intent);



        }
    }


}
