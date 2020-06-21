package com.proyectoandroid.safety;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Mensaje {

    private Context context;

    public Mensaje(Context context){
        this.context = context;
    }

    public void enviarMensaje(){
        String numero = "asd";
        String mensaje = "asd2";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(numero,null,mensaje,null,null);
        Toast.makeText(context,"Mensaje enviado",Toast.LENGTH_SHORT).show();
    }
}
