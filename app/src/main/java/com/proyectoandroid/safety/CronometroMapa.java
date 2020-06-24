package com.proyectoandroid.safety;

import android.widget.TextView;

public class CronometroMapa extends Thread {

    private TextView cronom;

    public CronometroMapa(TextView text){
        this.cronom=text;
    }

    public void run(){
        try{
            int x = 0;
            while(MapsActivity.isOn){
                Thread.sleep(1000);
                ejecutarHiloCronometro(x);
                x++;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void ejecutarHiloCronometro(int x) {

        MapsActivity.seg ++;
        if(MapsActivity.seg>59){
            MapsActivity.seg=0;
            MapsActivity.minutos++;
            if(MapsActivity.minutos>59){
                MapsActivity.minutos=0;
                MapsActivity.horas++;
            }
        }
        String textSeg = "", textMin = "", textHora="";
        if(MapsActivity.seg<10){
            textSeg="0"+MapsActivity.seg;

        }else{
            textSeg= ""+MapsActivity.seg;
        }

        if(MapsActivity.minutos<10){
            textMin="0"+MapsActivity.minutos;

        }else{
            textMin= ""+MapsActivity.minutos;
        }

        if(MapsActivity.horas<10){
            textHora="0"+MapsActivity.horas;

        }else{
            textHora= ""+MapsActivity.horas;
        }

        String reloj = textHora+":"+textMin+":"+textSeg;

        cronom.setText(reloj);

    }
}
