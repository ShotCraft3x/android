package com.proyectoandroid.safety;

import androidx.annotation.NonNull;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.maps.android.PolyUtil;
import com.proyectoandroid.directionhelpers.FetchURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import directionhelpers.TaskLoadedCallback;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback,
        LocationListener,BottomNavigationView.OnNavigationItemSelectedListener{

    private int permisoubicacion;

    private BottomNavigationView navigationview;
    private GoogleMap mMap;
    Fragment fragmentseleccionado = null;
    private Marker marcador;

    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;
    private double lat = 0.0;
    private double lng = 0.0;

    Polyline currentPolyline;
    private MarkerOptions place1, place2;

    //-------------- Variables para trazar ruta -------- JSON
    private JSONObject jso;

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


        //---------------- AQUI SE TRAZA LA RUTA -----------------------
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+ lat+","+lng+"&destination=-29.8905845,-71.2474624&key=AIzaSyB0ZS8JiCRLEiFui1GZi57UBKFCAenAymQ";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jso = new JSONObject(response);
                    trazarRuta(jso);
                    Log.i("jsonRuta:",""+response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);



    }

    private void trazarRuta(JSONObject jso) {
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;

        try {
            jRoutes = jso.getJSONArray("routes");
            for (int i=0; i<jRoutes.length();i++){

                jLegs = ((JSONObject)(jRoutes.get(i))).getJSONArray("legs");

                for (int j=0; j<jLegs.length();j++){

                    jSteps = ((JSONObject)jLegs.get(j)).getJSONArray("steps");

                    for (int k = 0; k<jSteps.length();k++){


                        String polyline = ""+((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                        Log.i("end",""+polyline);
                        List<LatLng> list = PolyUtil.decode(polyline);
                        mMap.addPolyline(new PolylineOptions().addAll(list).color(Color.GRAY).width(10));
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        Toast.makeText(getApplicationContext(),"LLego aquiiii",Toast.LENGTH_LONG).show();
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

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onLocationChanged(Location location) {
        marcador.remove();
        actualizarUbicacion(location);



        /*LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(getApplicationContext(),"Latitud: " + location.getLatitude(), Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);*/
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {




    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //metodo para las opciones de la BottomNavigationView
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_iniciarruta){
            Intent intent = new Intent(this.getApplicationContext(),IniciarRuta.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.nav_descubre){
            Intent intent = new Intent(this.getApplicationContext(),Descubre.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_config){
            Intent intent = new Intent(this.getApplicationContext(),Configuracion.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_estadisticas){
            Intent intent = new Intent(this.getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.nav_rutasrealizadas){
            Intent intent = new Intent(this.getApplicationContext(),RutasRealizadas.class);
            startActivity(intent);

        }



        return false;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}
