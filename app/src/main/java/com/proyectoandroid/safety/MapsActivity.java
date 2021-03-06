package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.AttributeSet;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        LocationListener, BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private int permisoubicacion;

    private BottomNavigationView navigationview;
    private GoogleMap mMap;
    private Marker marcador;

    private double lat = 0.0;
    private double lng = 0.0;

    //Componentes del layout
    private Button btnpanico;
    private Button btnpausar;
    private Button btncomenzar;
    private Button btnparar;

    //Variables para el PIN
    private boolean seBloqueoPantalla;
    private boolean cambioAplicacion;
    private TextView crono;
    static int seg = 0, minutos = 0, horas = 0;
    static boolean isOn = true;
    boolean corriendo = false;

    //GPS
    private boolean posicion = false;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    //Intent de inicio de ruta
    Double latruta = 0.0;
    Double lngruta = 0.0;
    String nombreruta = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        navigationview = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationview.setOnNavigationItemSelectedListener(this);

        //Referencia para el inicio de sesion
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnpanico = (Button) findViewById(R.id.btnpanico);
        btnpausar = (Button) findViewById(R.id.btnpausar);
        btncomenzar = (Button) findViewById(R.id.btncomenzar);
        btnparar = (Button) findViewById(R.id.btnparar);
        crono = (TextView) findViewById(R.id.txtcronometro);

        //Asignacion de eventos a los botones
        btnpanico.setOnClickListener(this);
        btnpausar.setOnClickListener(this);
        btncomenzar.setOnClickListener(this);
        btnparar.setOnClickListener(this);

        crono.setText("00:00:00");

        //Inicio del cronometro por parte de una ruta tomada con safety
        Intent intent =getIntent();
        Bundle b=intent.getExtras();
        if(b!=null){
            int res = b.getInt("op");
            latruta = b.getDouble("lat");
            lngruta = b.getDouble("lng");
            nombreruta = b.getString("name");
            if(res == 1){
                iniciarCronometro();
                //Se realiza el llamado al marcador para que aparezca en el mapa
                //setMarcadorIniciado(lat,lng,nombreruta);
            }

        }




        //Esta parte es para recibir el cronometro en caso de que se inicie una ruta con google map
        miServicioNotificacion.isOn = false;
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("Counter");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onReceive(Context context, Intent intent) {

                if (miServicioNotificacion.isOn == false) {
                    //Aqui se para la notificacion
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.deleteNotificationChannel(miServicioNotificacion.NOTIFICATION_CHANNEL_ID);
                }

                int s = intent.getIntExtra("seg", 0);
                int m = intent.getIntExtra("min", 0);
                int h = intent.getIntExtra("horas", 0);
                s = s - 1;
                String textSeg = "", textMin = "", textHora = "";

                if (s < 10) {
                    textSeg = "0" + s;

                } else {
                    textSeg = "" + s;
                }

                if (m < 10) {
                    textMin = "0" + m;

                } else {
                    textMin = "" + m;
                }

                if (h < 10) {
                    textHora = "0" + h;

                } else {
                    textHora = "" + h;
                }


                String reloj = textHora + ":" + textMin + ":" + textSeg;
                crono.setText(reloj);

            }
        };

        registerReceiver(broadcastReceiver, intentFilter);

        //setCronometro();

    }




    public void iniciarCronometro() {
        if (isOn == true) {
            CronometroMapa miCronometro = new CronometroMapa(crono);
            miCronometro.start();


        }
    }

    //Aqui se inicia un marcador en el mapa dependiendo de la punto iniciado desde DetalleRutaRealizadas
    public void setMarcadorIniciado(GoogleMap map,Double lat, Double lng,String nombre){
        mMap = map;
        LatLng p1 = new LatLng(lat, lng);
            mMap.addMarker(new MarkerOptions().position(p1).title(nombre).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


    }


    //Metodo para setear los  marcadores en el mapa
    public void Marcadores(GoogleMap map) {
        mMap = map;
        LatLng p1 = new LatLng(-29.922705, -71.2726555);
        mMap.addMarker(new MarkerOptions().position(p1).title("Bicimania Elite Store").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        LatLng p2 = new LatLng(-29.915983, -71.2678595);
        mMap.addMarker(new MarkerOptions().position(p2).title("Thoros Bikes").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


        LatLng p3 = new LatLng(-29.8905845, -71.2474624);
        mMap.addMarker(new MarkerOptions().position(p3).title("Cycles Serena").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));


    }


    public void AgregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);

        //CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi posicion actualll"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(14).bearing(80).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 15000, 0, this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //Toast.makeText(getApplicationContext(),"Entro 2",Toast.LENGTH_LONG).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        miUbicacion();

        //this.Marcadores(googleMap);
        if(!nombreruta.isEmpty()) {
            setMarcadorIniciado(mMap, latruta, lngruta, nombreruta);
        }




    }

    @Override
    public void onLocationChanged(Location location) {
        marcador.remove();
        actualizarUbicacion(location);
        posicion = true;
        //Toast.makeText(getApplicationContext(),"Cambio de lugar",Toast.LENGTH_LONG).show();
        /*LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(getApplicationContext(),"Latitud: " + location.getLatitude(), Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);*/    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

        //Toast.makeText(getApplicationContext(),"Estado cambiado",Toast.LENGTH_LONG).show();


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
            Intent intent = new Intent(this.getApplicationContext(), Descubre.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_config){
            if(mAuth.getCurrentUser()!=null) {
                Intent intent = new Intent(this.getApplicationContext(), Configuracion.class);
                startActivity(intent);
            }else{
                MensajeSesion();
            }
        }

        if(item.getItemId() == R.id.nav_estadisticas){
            Intent intent = new Intent(this.getApplicationContext(), Estadistica.class);
            startActivity(intent);

        }

        if(item.getItemId() == R.id.nav_rutasrealizadas){
            if(mAuth.getCurrentUser()!=null) {
                Intent visorRutas = new Intent(this.getApplicationContext(), RutasRealizadas.class);
                startActivity(visorRutas);
            }else{
                this.MensajeSesion();
            }

        }

        if(item.getItemId() == R.id.nav_fotos){
            if(mAuth.getCurrentUser()!=null) {
                Intent visorFotos = new Intent(this.getApplicationContext(), FotosActivity.class);

                startActivity(visorFotos);
            }else{
                this.MensajeSesion();
            }

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

    public void MensajeSesion () {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sesion");
        builder.setMessage("Debes iniciar sesion para usar esta opción");

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(intent);

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //Metodos para la captura de los eventos del boton
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnpausar:
                if(mAuth.getCurrentUser()!=null) {
                    isOn = false;
                    corriendo = false;
                    break;
                }else{
                    MensajeSesion();
                    break;
                }
            case R.id.btncomenzar:
                if(corriendo==false) {
                    if(mAuth.getCurrentUser()!=null) {
                        isOn = true;
                        corriendo = true;
                        iniciarCronometro();
                        btnpausar.setEnabled(true);
                        btnparar.setEnabled(true);
                        btncomenzar.setText("Seguir");
                        Toast.makeText(getApplicationContext(), "Seguimiento habilitado", Toast.LENGTH_LONG).show();
                        break;
                    }else{
                        MensajeSesion();
                        break;
                    }
                }
            case R.id.btnparar:
                if(mAuth.getCurrentUser()!=null) {
                    Toast.makeText(getApplicationContext(), "Trayecto finalizado", Toast.LENGTH_LONG).show();
                    btncomenzar.setEnabled(true);
                    btncomenzar.setText("Comenzar");
                    isOn = false;
                    corriendo = false;
                    seg = 0;
                    minutos = 0;
                    horas = 0;
                    crono.setText("00:00:00");
                    Intent myIntent = new Intent(this, PublicarFoto.class);
                    startActivity(myIntent);

                    break;
                }else{
                    MensajeSesion();
                    break;
                }

            case R.id.btnpanico:
                if(mAuth.getCurrentUser()!=null) {
                    Mensaje ms = new Mensaje(this);
                    String mensaje = "https://maps.google.com/?q=" + lat + "," + lng;
                    ms.enviarMensaje("Tu usuario de confianza necesita ayuda: " + mensaje);
                    break;
                }else{
                    MensajeSesion();
                    break;
                }

        }
    }
}
