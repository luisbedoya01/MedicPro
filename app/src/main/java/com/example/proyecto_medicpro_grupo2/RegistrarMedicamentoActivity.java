package com.example.proyecto_medicpro_grupo2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Medicamento;
import com.example.proyecto_medicpro_grupo2.utiles.Utils;

import java.util.ArrayList;

public class RegistrarMedicamentoActivity extends AppCompatActivity {

    EditText nombresApellidos, correoElectronico, nombreMedicamento, dosisMedicamento;
    Button btnMedicamento;

    ListView listaMedicamentos;

    ArrayList<Medicamento> medicamentosList;
    ArrayList<String> listMedicamentos;

    ArrayAdapter<String> adapterMedicamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_medicamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombresApellidos = findViewById(R.id.editTextNombresApellidos);
        correoElectronico = findViewById(R.id.editTextEmailUsuario);
        nombreMedicamento = findViewById(R.id.editTextMedicamento);
        dosisMedicamento = findViewById(R.id.editTextDosis);
        btnMedicamento = findViewById(R.id.btnAgregarMedicamento);
        listaMedicamentos = findViewById(R.id.listViewMedicamentos);

        medicamentosList = new ArrayList<>();
        listMedicamentos = new ArrayList<>();
        adapterMedicamento = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listMedicamentos);

        listaMedicamentos.setAdapter(adapterMedicamento);

        btnMedicamento.setOnClickListener(v -> agregarMedicamento());

        SharedPreferences preferences = getSharedPreferences("usuarioData", Context.MODE_PRIVATE);
        String nombreUsuario = preferences.getString("nombreUsuario", "");
        String correo = preferences.getString("correoUsuario", "");

        nombresApellidos.setText(nombreUsuario);
        correoElectronico.setText(correo);




    }

    private void agregarMedicamento() {
        String nomMedicamento = nombreMedicamento.getText().toString();
        String dosis = dosisMedicamento.getText().toString();

        if (!nomMedicamento.isEmpty() && !dosis.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioData", Context.MODE_PRIVATE);
            int idUsuario = sharedPreferences.getInt("idUsuario", -1);

            if (idUsuario != -1) {
                for (Medicamento med : medicamentosList) {
                    if (med.getNombre().equals(nomMedicamento) && med.getDosis().equals(dosis)) {
                        Utils.toastError(this, "El medicamento ya existe en la lista");
                        return;
                    }
                }

                Medicamento medicamento = new Medicamento(nomMedicamento, dosis, idUsuario);
                medicamentosList.add(medicamento);

                String medicamentoCompleto = nomMedicamento + " - " + dosis;
                listMedicamentos.add(medicamentoCompleto);
                adapterMedicamento.notifyDataSetChanged();
                nombreMedicamento.setText("");
                dosisMedicamento.setText("");
            } else {
                Utils.toastError(this, "No se pudo obtener el id del usuario");
            }
        } else {
            Utils.toastVacio(this, "Por favor, ingrese el nombre del medicamento");
        }
    }


    public void guardarMedicamentos(View view) {
        for (Medicamento med : medicamentosList) {
            guardarMedicamentosBD(med);
        }
        Utils.toastOK(this, "Medicamentos guardados correctamente");
        medicamentosList.clear();
        listMedicamentos.clear();
        adapterMedicamento.notifyDataSetChanged();
    }

    public void guardarMedicamentosBD(Medicamento med) {
        MyOpenHelper dbMedicPro = new MyOpenHelper(this);
        final SQLiteDatabase dbMedicProMode = dbMedicPro.getWritableDatabase();

        if (dbMedicProMode != null) {
            // Verificar si el medicamento ya se encuentra registrado
            Cursor cursor = dbMedicProMode.rawQuery("SELECT * FROM Medicamento WHERE nombre = ? AND idUsuario = ?",
                    new String[]{med.getNombre(), String.valueOf(med.getIdUsuario())});
            if (cursor.moveToFirst()) {
                Utils.toastError(this, "El medicamento ya se encuentra registrado para este usuario");
                cursor.close();
                return;
            }
            cursor.close();
            ContentValues values = new ContentValues();
            values.put("nombre", med.getNombre());
            values.put("dosis", med.getDosis());
            values.put("idUsuario", med.getIdUsuario());

            dbMedicProMode.insert("Medicamento", null, values);
        }
    }

    private void limpiarMedicamentos() {
        nombreMedicamento.setText("");
        dosisMedicamento.setText("");
    }



}