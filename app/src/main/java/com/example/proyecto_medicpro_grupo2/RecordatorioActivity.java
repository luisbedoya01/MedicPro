package com.example.proyecto_medicpro_grupo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Utils;

public class RecordatorioActivity extends AppCompatActivity {
    TextView nombreMedicamento, hora, fecha;
    private MyOpenHelper dbMedicPro;

    private int idRecordatorio, idEstado;

    private String medicamento, horaRecordatorio, fechaRecordatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recordatorio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        idRecordatorio = intent.getIntExtra("idRecordatorio", -1);
        medicamento = intent.getStringExtra("nombreMedicamento");
        horaRecordatorio = intent.getStringExtra("horaRecordatorio");
        fechaRecordatorio = intent.getStringExtra("fechaRecordatorio");

        nombreMedicamento = findViewById(R.id.txtRecordatorioName);
        hora = findViewById(R.id.txtRecordatorioHour);
        fecha = findViewById(R.id.txtRecordatorioDate);

        nombreMedicamento.setText(medicamento);
        hora.setText(horaRecordatorio);
        fecha.setText(fechaRecordatorio);

        /*
        nombreMedicamento = findViewById(R.id.txtMedicamento);
        hora = findViewById(R.id.txtHoraMedicamento);
        fecha = findViewById(R.id.txtFechaMedicamento);*/
    }

    public void completarRecordatorio(View view) {
        dbMedicPro = new MyOpenHelper(this);
        idEstado = 2;
        dbMedicPro.actualizarEstadoRecordatorio(idRecordatorio, idEstado);
        Utils.toastOK(this, "Recordatorio completado");
        finish();
    }

    public void posponerRecordatorio(View view) {
        dbMedicPro = new MyOpenHelper(this);
        idEstado = 3;
        dbMedicPro.actualizarEstadoRecordatorio(idRecordatorio, idEstado);
        Utils.toastOK(this, "Recordatorio pospuesto");
        finish();
    }

}