package com.proyectoandroid.safety;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.proyectoandroid.safety.R;

public class miServicioNotificacion extends Service {


    //Cronometro de la notificacion
    static int seg = 0, minutos = 0, horas = 0;
    static boolean isOn = false;
    static boolean corriendo = false;
    private String reloj;

    //Notificacion de ruta comenzada
    private PendingIntent pendingIntent;
    private PendingIntent mapsIntent;

    static String NOTIFICATION_CHANNEL_ID = "com.proyectoandroid.safety.miServicioNotificacion";
    static String channelName = "My Background Service";

    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void mapsPendingIntent() {
        Intent intent = new Intent(this,MapsActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MapsActivity.class);
        stackBuilder.addNextIntent(intent);
        mapsIntent = stackBuilder.getPendingIntent(1,PendingIntent.FLAG_CANCEL_CURRENT);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void crearNotificacion(){
        //mapsPendingIntent();
        Intent notificacionIntent = new Intent(this, MapsActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificacionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("Ruta iniciada")
                .setContentText("Acabas de iniciar una ruta: " + reloj)
                .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                .setColor(Color.BLUE)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_parar, "Parar", pendingIntent)
                .addAction(R.drawable.ic_parar, "Panico", pendingIntent)
                .addAction(R.drawable.ic_parar, "Pausar", pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(1, notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        seg = 0;
        minutos = 0;
        horas = 0;
        Thread thread = new Thread(){
            public void run() {
                while (isOn) {
                    Intent intentlocal = new Intent();
                    intentlocal.setAction("Counter");
                    crearNotificacion();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    seg++;
                    if (seg > 59) {
                        seg = 0;
                        minutos++;
                        if (minutos > 59) {
                            minutos = 0;
                            horas++;
                        }
                    }
                    String textSeg = "", textMin = "", textHora = "";
                    if (seg < 10) {
                        textSeg = "0" + seg;

                    } else {
                        textSeg = "" + seg;
                    }

                    if (minutos < 10) {
                        textMin = "0" + minutos;

                    } else {
                        textMin = "" + minutos;
                    }

                    if (horas < 10) {
                        textHora = "0" + horas;

                    } else {
                        textHora = "" + horas;
                    }

                    reloj = textHora + ":" + textMin + ":" + textSeg;


                    intentlocal.putExtra("seg", seg);
                    intentlocal.putExtra("min", minutos);
                    intentlocal.putExtra("horas", horas);
                    sendBroadcast(intentlocal);

                }
            }

        };
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }
}




