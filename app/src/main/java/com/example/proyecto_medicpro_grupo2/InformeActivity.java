package com.example.proyecto_medicpro_grupo2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_medicpro_grupo2.utiles.ListAdapter;
import com.example.proyecto_medicpro_grupo2.utiles.ListAdapterMed;
import com.example.proyecto_medicpro_grupo2.utiles.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class InformeActivity extends AppCompatActivity {
    List<Medicamento> listaMedicamentos;
    private int idUsuario;
    private MyOpenHelper dbMedicPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_informe);
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

    private void inIt() {
        listaMedicamentos = new ArrayList<>();
        listaMedicamentos = dbMedicPro.obtenerMedicamentosConCumplimiento(idUsuario);

        if (!listaMedicamentos.isEmpty()) {
            ListAdapterMed listAdapterMed = new ListAdapterMed(listaMedicamentos, this);
            RecyclerView recyclerView = findViewById(R.id.viewMedicamentos);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapterMed);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Atención").
                    setMessage("No hay medicamentos registrados").
                    setPositiveButton("Aceptar", (dialog, which) -> {
                        dialog.dismiss();
                        Intent intent = new Intent(this, inicio.class);
                        startActivity(intent);
                    }).show();
        }


    }
}