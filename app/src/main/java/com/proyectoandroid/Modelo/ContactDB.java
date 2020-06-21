package com.proyectoandroid.Modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.proyectoandroid.safety.Contactos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactDB {


    private Context context;

    private DbHelper helper;
    private SQLiteDatabase db;

    public ContactDB(Context context) {
        this.context = context;
    }

    public void registrarDatos(String nombre, String numero){
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        if(!(nombre.isEmpty() && numero.isEmpty())) {
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("numero", numero);
                Cursor fila = db.rawQuery("select nombre from contactos where numero = '" + numero + "'", null);
                if (fila.moveToFirst()) {
                    Toast.makeText(context, "El contacto ya esta ingresado", Toast.LENGTH_LONG).show();
                } else {
                    db.insert("contactos", null, registro);
                    Toast.makeText(context, "Se ingreso correctamente " + nombre  + numero,  Toast.LENGTH_LONG).show();
                }
            db.close();
        }else{
            Toast.makeText(context,"Estan vacios",Toast.LENGTH_SHORT).show();
        }
    }

    public List<Contactos> consultarDatos(){
        List<Contactos> listacontactos = new ArrayList<>();
        Contactos contact = null;
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        Cursor fila  = db.rawQuery("select nombre, numero from contactos", null);
        while(fila.moveToNext()){
            contact = new Contactos();
            contact.setNombre(fila.getString(fila.getColumnIndex("nombre")));
            contact.setNumero(fila.getString(fila.getColumnIndex("numero")));
            listacontactos.add(contact);
        }
        db.close();
        return listacontactos;

    }
}
