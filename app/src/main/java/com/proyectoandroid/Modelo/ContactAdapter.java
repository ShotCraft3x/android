package com.proyectoandroid.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.proyectoandroid.safety.Contactos;
import com.proyectoandroid.safety.R;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contactos> {

    private List<Contactos> milista;
    private Context  mcontext;
    private int resource_layout;


    public ContactAdapter(@NonNull Context context, int resource, List<Contactos> objects) {
        super(context, resource, objects);
        this.milista = objects;
        mcontext = context;

        this.resource_layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(mcontext).inflate(R.layout.adapter_contactos,null);
        }

        Contactos contactos = milista.get(position);
        TextView txtnombre = view.findViewById(R.id.txtnombre);
        TextView txtnumero = view.findViewById(R.id.txtnumero);

        txtnombre.setText(contactos.getNombre());
        txtnumero.setText(contactos.getNumero());


        return view;



    }
}


