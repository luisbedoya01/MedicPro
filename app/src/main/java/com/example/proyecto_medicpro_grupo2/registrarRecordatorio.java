package com.example.proyecto_medicpro_grupo2;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Medicamento;
import com.example.proyecto_medicpro_grupo2.utiles.Recordatorio;
import com.example.proyecto_medicpro_grupo2.utiles.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class registrarRecordatorio extends AppCompatActivity {

    ImageView btnHora, btnFecha;
    EditText txtNombreMedicamento, txtHora, txtFecha;

    Spinner spinnerMed;

    private int idUsuario;

    private MyOpenHelper dbMedicPro;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private Calendar fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_recordatorio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnHora = findViewById(R.id.btnHora);
        btnFecha = findViewById(R.id.btnFecha);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        fecha = Calendar.getInstance();

        spinnerMed = findViewById(R.id.spnMedicamentos);

        String fechaActual = String.format("%02d/%02d/%d",
                fecha.get(Calendar.DAY_OF_MONTH),
                fecha.get(Calendar.MONTH) + 1,
                fecha.get(Calendar.YEAR));
        txtFecha.setText(fechaActual);

        btnHora.setOnClickListener(v -> abrirDialogoHora());

        btnFecha.setOnClickListener(v -> abrirDialogoFecha());

        createNotificationChannel();

        SharedPreferences preferences = getSharedPreferences("usuarioData", Context.MODE_PRIVATE);
        idUsuario = preferences.getInt("idUsuario", -1);

        dbMedicPro = new MyOpenHelper(this);

        List<Medicamento> medicamentos = dbMedicPro.obtenerMedicamentos(idUsuario);
        List<Medicamento> items = new ArrayList<>();

        for (Medicamento item: medicamentos) {
            items.add(new Medicamento(item.getIdMedicamento(), item.getNombre()));
        }

        ArrayAdapter<Medicamento> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMed.setAdapter(adapter);
    }

    public void abrirDialogoHora() {
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (timePicker, selectedHour, selectedMinute) -> {
                    txtHora.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                }, hora, minuto, true);
        timePickerDialog.show();
    }

    public void abrirDialogoFecha() {
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            txtFecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            fecha.set(year, month, dayOfMonth);
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    private List<Recordatorio> recordatorios = new ArrayList<>();

    public void guardarRecordatorios (View view) {

        Medicamento medicamentoSeleccionado = (Medicamento) spinnerMed.getSelectedItem();
        int idMedicamento = medicamentoSeleccionado.getIdMedicamento();
        String hora = txtHora.getText().toString();
        String fecha = txtFecha.getText().toString();
        int idUser = idUsuario;
        int numeroRecordatorio = obtenerNumeroRecordatorios();
        int estado = 1;
        String nombreRecordatorio = "Recordatorio N° " + (numeroRecordatorio + 1);

        Recordatorio recordatorio = new Recordatorio(nombreRecordatorio, hora, fecha, idMedicamento, idUser, estado);

        long resultado = dbMedicPro.agregarRecordatorios(recordatorio);

        Log.d("Recordatorio", "ID: " + resultado);

        String[] fechaParts = fecha.split("/");
        String[] horaParts = hora.split(":");
        int day = Integer.parseInt(fechaParts[0]);
        int month = Integer.parseInt(fechaParts[1]) - 1; // Meses son de 0 a 11 en Calendar
        int year = Integer.parseInt(fechaParts[2]);
        int hour = Integer.parseInt(horaParts[0]);
        int minute = Integer.parseInt(horaParts[1]);

        Calendar calendario = Calendar.getInstance();
        calendario.set(year, month, day, hour, minute, 0);
        Log.d("CALENDARIO: ", "Fecha: " + calendario.getTimeInMillis());

        if (resultado != -1) {
            setAlarma(calendario, (int) resultado);
            Utils.toastOK(this, "Recordatorio guardado");
            regresarInicio(view);
        } else {
            Utils.toastError(this, "Error al guardar el recordatorio");
        }
    }

    private void regresarInicio(View view) {
        Intent ventanaInicio = new Intent(this, inicio.class);
        startActivity(ventanaInicio);
    }

    private void createNotificationChannel () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "medicpro_channel";
            String descripcion = "Canal de notificaciones de MedicPro";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("MedicPro", name, importance);
            channel.setDescription(descripcion);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint({"UnspecifiedImmutableFlag", "ScheduleExactAlarm"})
    private void setAlarma(Calendar fecha, int idRecordatorio) {
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("idRecordatorio", idRecordatorio);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, fecha.getTimeInMillis(), pendingIntent);
    }

    private int obtenerNumeroRecordatorios() {
        MyOpenHelper dbMedicPro = new MyOpenHelper(this);
        final SQLiteDatabase dbMedicProMode = dbMedicPro.getReadableDatabase();
        int idUser = idUsuario;

        Cursor cursor = dbMedicProMode.rawQuery("SELECT COUNT (*) FROM Recordatorio WHERE idUsuario = ?", new String[]{String.valueOf(idUser)});
        int numRecordatorios = 0;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                numRecordatorios = cursor.getInt(0);
            }
            cursor.close();
        }
        return numRecordatorios;
    }
}