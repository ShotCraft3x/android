package com.proyectoandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.proyectoandroid.safety.R;

public class Adaptador extends BaseAdapter {
    //LayoutInflater es para instanciar el archivo de diseño xml
    private static LayoutInflater inflater=null;
    Context contexto;
    String[][] datos;
    int[] datosImg;

    public Adaptador(Context conexto,String[][] datos,int[] imagenes)
    {
        this.contexto=conexto;
        this.datos=datos;
        this.datosImg=imagenes;

        inflater = (LayoutInflater) conexto.getSystemService(conexto.LAYOUT_INFLATER_SERVICE);
    }
    //declarar los elementos y asignar el valor
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elemento_lista,null);
        TextView titulo=(TextView) vista.findViewById(R.id.txt_fecha);
        TextView descripcion=(TextView) vista.findViewById(R.id.txt_distancia);
        ImageView imagen= (ImageView) vista.findViewById(R.id.ivImagen);
        RatingBar calificacion =(RatingBar) vista.findViewById(R.id.ratingBar);

        titulo.setText(datos[i][0]);
        descripcion.setText(datos[i][1]);
        imagen.setImageResource(datosImg[i]);
        calificacion.setProgress(Integer.valueOf(datos[i][2]));

        return vista;
    }

    //captura el total de elementos
    @Override
    public int getCount() {
        return datosImg.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
