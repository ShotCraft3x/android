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
    static boolean isOn = true;
    static boolean corriendo = false;
    private String reloj;

    //Notificacion de ruta comenzada
    private PendingIntent pendingIntent;
    private PendingIntent mapsIntent;
    final static String CHANNEL_ID = "Notificacion";
    private final static int NOTIFICACION_ID = 0;

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
        try {
            Intent notificacionIntent = new Intent(this, MapsActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificacionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                    .setContentTitle("Ruta iniciada")
                    .setContentText("Acabas de iniciar una ruta: " + reloj)
                    .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                    .setColor(Color.BLUE)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(R.drawable.ic_parar, "Parar", pendingIntent)
                    .addAction(R.drawable.ic_parar, "Panico", pendingIntent)
                    .addAction(R.drawable.ic_parar, "Pausar", pendingIntent)
                    .setAutoCancel(true)
                    .build();    startForeground(1, notification);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Ruta iniciada", NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        /*

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_directions_run_black_24dp);
        builder.setContentTitle("Ruta iniciada");
        builder.setContentText("Acabas de iniciar una ruta: " + tiempo);
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT); //Prioridad de notificacion
        builder.setLights(Color.MAGENTA,1000,1000); //Luz
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000}); //Vibracion
        builder.setDefaults(Notification.DEFAULT_SOUND); //Sonido
        builder.setUsesChronometer(true);
        builder.setContentIntent(mapsIntent);
        builder.addAction(R.drawable.ic_parar,"Parar",mapsIntent);
        builder.addAction(R.drawable.ic_parar,"Panico",mapsIntent);
        builder.addAction(R.drawable.ic_parar,"Pausar",mapsIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
*/
    }

    //Inicio de cronometro
    private void ejecutarHiloCronometro() {
        Thread thread = new Thread(){
            @RequiresApi(api = Build.VERSION_CODES.O)
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


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        seg = 0;
        minutos = 0;
        horas = 0;

        ejecutarHiloCronometro();

        return super.onStartCommand(intent, flags, startId);
    }
}




