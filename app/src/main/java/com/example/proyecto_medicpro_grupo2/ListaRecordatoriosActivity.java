package com.example.proyecto_medicpro_grupo2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_medicpro_grupo2.utiles.ListAdapter;
import com.example.proyecto_medicpro_grupo2.utiles.ListElement;
import com.example.proyecto_medicpro_grupo2.utiles.Recordatorio;

import java.util.ArrayList;
import java.util.List;

public class ListaRecordatoriosActivity extends AppCompatActivity {
    List<Recordatorio> listaRecordatorio;
    private int idUsuario;

    private MyOpenHelper dbMedicPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_recordatorios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences preferences = getSharedPreferences("usuarioData", Context.MODE_PRIVATE);
        idUsuario = preferences.getInt("idUsuario", -1);

        dbMedicPro = new MyOpenHelper(this);
        inIt();
    }

    public void inIt(){
        listaRecordatorio = new ArrayList<>();
        listaRecordatorio = dbMedicPro.obtenerRecordatorios(idUsuario);

        if (listaRecordatorio.size() > 0) {
            ListAdapter listAdapter = new ListAdapter(listaRecordatorio, this);
            RecyclerView recyclerView = findViewById(R.id.viewRecordatorios);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Atención")
                    .setMessage("No hay recordatorios registrados")
                    .setPositiveButton("Aceptar", (dialog, which) -> {
                        dialog.dismiss();
                        Intent intent = new Intent(this, inicio.class);
                        startActivity(intent);
                    })
                    .show();

        }

    }
}