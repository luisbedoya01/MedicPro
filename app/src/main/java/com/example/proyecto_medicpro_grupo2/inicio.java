package com.example.proyecto_medicpro_grupo2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class inicio extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }

    public void cerrarSesion(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Utils.toastOK(this, "Sesión cerrada correctamente");
        finish();
    }
    public void listadoRecordatorios (View view) {
        Intent recordatorios = new Intent(this, ListaRecordatoriosActivity.class);
        startActivity(recordatorios);
    }

    public void ventanaInforme(View v) {
        Intent informe = new Intent(this, InformeActivity.class);
        startActivity(informe);
    }

    public void VentanaRecordatorio(View v){
        Intent recordatorio= new Intent( v.getContext(), registrarRecordatorio.class);
        startActivity(recordatorio);


    }
    public void PerfilUsuario (View view) {
        Intent perfilUsuario = new Intent(view.getContext(), RegistrarMedicamentoActivity.class);
        startActivity(perfilUsuario);
    }


}