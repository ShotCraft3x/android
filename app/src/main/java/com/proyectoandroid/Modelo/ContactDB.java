package com.proyectoandroid.Modelo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.proyectoandroid.safety.Configuracion;
import com.proyectoandroid.safety.Contactos;
import com.proyectoandroid.safety.MainActivity;

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

    public void eliminarDatos(){
        helper = new DbHelper(context);
        final CharSequence[] opciones = {"Aceptar" , "Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(context);
        alertOpciones.setTitle("Confirme");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(opciones[i].equals("Aceptar")){
                    db = helper.getWritableDatabase();

                    int cantidad_filas;
                    cantidad_filas = db.delete("contactos","",null);
                    if(cantidad_filas == 1){
                        Toast.makeText(context,"Los datos han sido eliminados",Toast.LENGTH_LONG).show();

                    }
                    db.close();
                }
                if(opciones[i].equals("Cancelar")){
                    Toast.makeText(context,"Eliminacion cancelada", Toast.LENGTH_LONG).show();
                }
            }
        });
        alertOpciones.show();
    }
}
