package com.proyectoandroid.safety;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.proyectoandroid.Modelo.ContactDB;

import java.util.ArrayList;
import java.util.List;

public class Mensaje {

    private Context context;

    public Mensaje(Context context){
        this.context = context;
    }

    public void enviarMensaje(String mensaje){
        List<Contactos> numeros = new ArrayList<>();

        ContactDB cb = new ContactDB(context);
        numeros = cb.consultarDatos();
        for(int i= 0;i<numeros.size();i++){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeros.get(i).getNumero(),null,mensaje,null,null);
        }

        Toast.makeText(context,"Mensaje enviado",Toast.LENGTH_SHORT).show();
    }
}
