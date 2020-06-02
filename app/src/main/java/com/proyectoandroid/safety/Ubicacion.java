package com.proyectoandroid.safety;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Ubicacion implements LocationListener {


    Context context;
    GoogleMap mMap;
    LocationManager locationManager;

    public Ubicacion(Context context, GoogleMap googleMap, LocationManager loc){
        this.context = context;
        this.mMap = googleMap;
        this.locationManager= loc;
    }





    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Toast.makeText(context,"Latitud: " + location.getLatitude(), Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        mMap.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
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
}
