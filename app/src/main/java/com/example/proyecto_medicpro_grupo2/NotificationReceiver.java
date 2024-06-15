package com.example.proyecto_medicpro_grupo2;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Recordatorio;

public class NotificationReceiver extends BroadcastReceiver  {
    private MyOpenHelper dbMedicPro;
    private int idRecordatorio;

    private int idUsuario;
    private SharedPreferences preferences;



    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        preferences = context.getSharedPreferences("usuarioData", Context.MODE_PRIVATE);
        idUsuario = preferences.getInt("idUsuario", -1);
        dbMedicPro = new MyOpenHelper(context);
        idRecordatorio = intent.getIntExtra("idRecordatorio", -1);
        if (idRecordatorio != -1) {
            Recordatorio recordatorio = dbMedicPro.obtenerRecordatorioPorId(idUsuario, idRecordatorio);
            if (recordatorio != null) {
                Intent i = new Intent(context, RecordatorioActivity.class);
                i.putExtra("idRecordatorio", recordatorio.getIdRecordatorio());
                i.putExtra("nombreMedicamento", recordatorio.getNombreMedicamento());
                i.putExtra("fechaRecordatorio", recordatorio.getFechaRecordatorio());
                i.putExtra("horaRecordatorio", recordatorio.getHoraRecordatorio());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MedicPro")
                        .setSmallIcon(R.drawable.ic_notificacion)
                        .setContentTitle("Recordatorio")
                        .setContentText("No olvides de tomar tu medicamento")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setOngoing(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                notificationManagerCompat.notify(200, builder.build());
            }
        }
    }
}

