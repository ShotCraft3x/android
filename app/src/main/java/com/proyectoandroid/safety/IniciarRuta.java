package com.proyectoandroid.safety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Bidi;

public class IniciarRuta extends AppCompatActivity implements View.OnClickListener{


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_ruta);
        btnpanico = (Button)findViewById(R.id.btnpanico);
        btnpausar = (Button)findViewById(R.id.btnpausar);
        btncomenzar = (Button)findViewById(R.id.btncomenzar);
        btnparar = (Button)findViewById(R.id.btnparar);

        //Asignacion de eventos a los botones
        btnpanico.setOnClickListener(this);
        btnpausar.setOnClickListener(this);
        btncomenzar.setOnClickListener(this);
        btnparar.setOnClickListener(this);

        //Se bloquean los botones al iniciar la actividad
        btnpausar.setEnabled(false);
        btnparar.setEnabled(false);

        crono = (TextView)findViewById(R.id.txtcronometro);

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
                isOn=true;
                break;
        }
    }
}


