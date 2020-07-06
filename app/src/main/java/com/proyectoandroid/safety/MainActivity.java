package com.proyectoandroid.safety;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static android.Manifest.permission.FOREGROUND_SERVICE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnomitir;
    private Button btnregistro;
    private Button btnsesion;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    private int MY_PERMISSIONS_REQUEST_SERVICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnomitir = (Button)findViewById(R.id.btn_invitado);
        btnsesion = (Button)findViewById(R.id.btn_iniciar);
        btnregistro = (Button)findViewById(R.id.btn_registro);
        btnomitir.setOnClickListener(this);
        btnregistro.setOnClickListener(this);
        btnsesion.setOnClickListener(this);
        this.pedirPermisoUbicacion();
        //this.pedirPermisoMensaje();
        this.pedirPermisoServicio();
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

    //Metodo para pedir permiso de la ubicaciond el GPS
    public void pedirPermisoServicio() {


        if (ActivityCompat.checkSelfPermission(this, FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{FOREGROUND_SERVICE},
                    MY_PERMISSIONS_REQUEST_SERVICE);

            return;
        }


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_invitado){
            Intent intent = new Intent(this.getApplicationContext(),MapsActivity.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.btn_registro){
            Intent intent = new Intent(this.getApplicationContext(),Registro.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.btn_iniciar){
            Intent intent = new Intent(this.getApplicationContext(),IniciarSesion.class);
            startActivity(intent);
        }
    }


}
