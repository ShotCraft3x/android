package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener,BottomNavigationView.OnNavigationItemSelectedListener,View.OnClickListener{

    private int permisoubicacion;
    private int tiempo = 0;

    private BottomNavigationView navigationview;
    private GoogleMap mMap;
    Fragment fragmentseleccionado = null;
    private Marker marcador;

    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private double lat = 0.0;
    private double lng = 0.0;


    private MarkerOptions place1, place2;


    public LocationListener locationListener = null;
    public Location location = null;


    //Componentes del layout
    private Button btnpanico;
    private Button btnpausar;
    private Button btncomenzar;
    private Button btnparar;


    //Variables para el PIN
    private boolean seBloqueoPantalla;
    private boolean cambioAplicacion;

    //Componentes para el cronometro
    private Handler h = new Handler();
    private Thread cronos;
    private TextView crono;
    private int mili= 0, seg = 0, minutos = 0, horas = 0;
    private boolean isOn = false;

    //GPS
    private boolean posicion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        navigationview = (BottomNavigationView)findViewById(R.id.bottomNavigationView);
        navigationview.setOnNavigationItemSelectedListener(this);

        btnpanico = (Button)findViewById(R.id.btnpanico);
        btnpausar = (Button)findViewById(R.id.btnpausar);
        btncomenzar = (Button)findViewById(R.id.btncomenzar);
        btnparar = (Button)findViewById(R.id.btnparar);
        crono = (TextView)findViewById(R.id.txtcronometro);

        //Asignacion de eventos a los botones
        btnpanico.setOnClickListener(this);
        btnpausar.setOnClickListener(this);
        btncomenzar.setOnClickListener(this);
        btnparar.setOnClickListener(this);
        setCronometro();

    }

    public void setCronometro(){


        //Se bloquean los botones al iniciar la actividad
        btnpausar.setEnabled(false);
        btnparar.setEnabled(false);
        cronos = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(isOn){
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mili++;
                        if(mili==999){
                            seg++;
                            mili=0;
                        }
                        if(seg == 59){
                            minutos ++;
                            seg=0;

                        }

                        if(minutos == 59){
                            horas ++;
                            minutos=0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String s="",mi="",hr="00";


                                if(seg<10){
                                    s="0"+seg;
                                }else{
                                    s=""+seg;
                                }
                                if(minutos<10){
                                    mi="0"+minutos;
                                }else{
                                    mi=""+minutos;
                                }
                                crono.setText(hr+":"+mi+":"+s);

                            }
                        });
                    }
                }
            }
        });
        cronos.start();
    }

    //Metodo para setear los  marcadores en el mapa
    public void Marcadores(GoogleMap map){
        mMap = map;
        LatLng p1 = new LatLng(-29.922705,-71.2726555);
        mMap.addMarker(new MarkerOptions().position(p1).title("Bicimania Elite Store").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        LatLng p2 = new LatLng(-29.915983,-71.2678595);
        mMap.addMarker(new MarkerOptions().position(p2).title("Thoros Bikes").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        LatLng p3 = new LatLng(-29.8905845,-71.2474624);
        mMap.addMarker(new MarkerOptions().position(p3).title("Cycles Serena").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));





        mMap.addMarker(new MarkerOptions().position(p1).title("Bicimania Elite Store\"").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(p2).title("Thoros Bikes").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(p3).title("Cycles Serena").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

    }


    public void AgregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);

        //CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi posicion actualll"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat,lng)).zoom(14).bearing(80).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));



        Toast.makeText(getApplicationContext(),"LLego aquiiii 333",Toast.LENGTH_LONG).show();




    }



    public void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            //Toast.makeText(getApplicationContext(),"Entro4",Toast.LENGTH_LONG).show();
            AgregarMarcador(lat, lng);
        }
    }

    public void miUbicacion() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    permisoubicacion);

            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //Locacion GPS o NETWORK
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,15000,0,this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        //Toast.makeText(getApplicationContext(),"Entro 2",Toast.LENGTH_LONG).show();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        miUbicacion();

        this.Marcadores(googleMap);


    }



    @Override
    public void onLocationChanged(Location location) {
        marcador.remove();
        actualizarUbicacion(location);
        posicion = true;
        Toast.makeText(getApplicationContext(),"Cambio de lugar",Toast.LENGTH_LONG).show();
        /*LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(getApplicationContext(),"Latitud: " + location.getLatitude(), Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);*/
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        Toast.makeText(getApplicationContext(),"Estado cambiado",Toast.LENGTH_LONG).show();


    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(getApplicationContext(),"Provedor activado",Toast.LENGTH_LONG).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("Ubicacion activada, vuelve a iniciar la app");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onProviderDisabled(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("Activa la ubicacion");
        builder.setPositiveButton("Aceptar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //metodo para las opciones de la BottomNavigationView
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_descubre){
            Intent intent = new Intent(this.getApplicationContext(),Descubre.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_config){
            Intent intent = new Intent(this.getApplicationContext(),Configuracion.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.nav_estadisticas){
            Intent intent = new Intent(this.getApplicationContext(),Estadistica.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.nav_rutasrealizadas){
            Intent visorDetalle=new Intent(this.getApplicationContext(), RutasRealizadas.class);

            startActivity(visorDetalle);

        }



        return false;
    }

    //Metodos para la pantalla del PIN
    @Override
    protected void onPause() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn = powerManager.isScreenOn();
        if (!isScreenOn) {
            Log.i("XD", "La pantalla ha sido bloqueada");
            seBloqueoPantalla = true;
        }
        super.onPause();
    }
    @Override
    protected void onStop() {
        cambioAplicacion = true;
        super.onStop();
    }
    @Override
    protected void onResume() {

        if (seBloqueoPantalla && cambioAplicacion) {
            //Redirecciona a la actividad Login!.
            Intent myIntent = new Intent(this, PassCode.class);
            startActivity(myIntent);
            //reiniciamos valores.
            seBloqueoPantalla = false;
            cambioAplicacion = false;
        }
        super.onResume();
    }


    //Metodos para la captura de los eventos del boton
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnpausar:
                isOn=false;
                break;
            case R.id.btncomenzar:
                Toast.makeText(getApplicationContext(),"Seguimiento habilitado",Toast.LENGTH_LONG).show();
                btnpausar.setEnabled(true);
                btnparar.setEnabled(true);
                btncomenzar.setText("Seguir");
                isOn=true;
                break;
            case R.id.btnparar:
                Toast.makeText(getApplicationContext(),"Trayecto finalizado",Toast.LENGTH_LONG).show();

                crono.setText("00:00:00");
                mili= 0;
                seg = 0;
                minutos = 0;
                horas = 0;
                btncomenzar.setEnabled(true);
                btncomenzar.setText("Comenzar");
                isOn=false;

                break;
        }
    }
}
