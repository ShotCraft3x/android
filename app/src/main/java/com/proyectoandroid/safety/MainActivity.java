package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnomitir;
    private Button btnregistro;
    private Button btnsesion;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnomitir = (Button)findViewById(R.id.omitir);
        btnsesion = (Button)findViewById(R.id.btnsesion);
        btnregistro = (Button)findViewById(R.id.btnregistro);
        btnomitir.setOnClickListener(this);
        btnregistro.setOnClickListener(this);
        btnsesion.setOnClickListener(this);
        this.pedirPermisoUbicacion();
        this.pedirPermisoMensaje();
    }

    //Metodo para pedir permiso de la ubicaciond el GPS
    public void pedirPermisoUbicacion() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }


    }

    public void pedirPermisoMensaje() {
        int permisocheck = ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS);

        if(permisocheck==PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }




    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.omitir){
            Intent intent = new Intent(this.getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.btnregistro){
            Intent intent = new Intent(this.getApplicationContext(),Registro.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.btnsesion){
            Intent intent = new Intent(this.getApplicationContext(),IniciarSesion.class);
            startActivity(intent);
        }
    }


}
