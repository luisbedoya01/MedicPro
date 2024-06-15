package com.example.proyecto_medicpro_grupo2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Usuario;
import com.example.proyecto_medicpro_grupo2.utiles.Utils;

public class LoginActivity extends AppCompatActivity {

    EditText correo, clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        correo = findViewById(R.id.editTextCorreo);
        clave = findViewById(R.id.editTextContrasenia);
    }

    public void RegistroUsuario (View view) {
        Intent intent = new Intent(this, RegistrarUsuarioActivity.class);
        startActivity(intent);
    }

    public void iniciarSesion (View view) {
        if (verificarCampos()) {
            inicioSesion();
        } else {
            Utils.toastVacio(this, "Por favor, complete todos los campos para iniciar sesión");
        }

    }

    @SuppressLint("Range")
    private void inicioSesion() {
        MyOpenHelper dbMedicPro = new MyOpenHelper(this);
        final SQLiteDatabase dbMedicProMode = dbMedicPro.getReadableDatabase();

        String email = correo.getText().toString();
        String password = clave.getText().toString();
        Cursor cursor = dbMedicProMode.rawQuery("SELECT * FROM Usuario WHERE correo = ? AND clave = ?", new String[]{email, password});

        if (cursor.moveToFirst()) {
            int idUsuario = cursor.getInt(cursor.getColumnIndex("idUsuario"));
            String nombreUsuario = cursor.getString(cursor.getColumnIndex("nombresApellidos"));
            String correoUsuario = cursor.getString(cursor.getColumnIndex("correo"));
            String claveUsuario = cursor.getString(cursor.getColumnIndex("clave"));

            Usuario user = new Usuario(idUsuario, nombreUsuario, correoUsuario, claveUsuario);

            //Guardar el id y nombre del usuario en un SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("usuarioData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("idUsuario", user.getIdUsuario());
            editor.putString("nombreUsuario", user.getNombresApellidos());
            editor.putString("correoUsuario", user.getCorreo());
            editor.apply();

            //Iniciar MainActivity
            Intent intent = new Intent(this, inicio.class);
            startActivity(intent);
            limpiarCampos();
        } else {
            Utils.toastError(this, "Correo o contraseña incorrectos");
            limpiarCampos();
        }
    }

    private boolean verificarCampos() {
        boolean isValido = true;
        EditText[] campos = {correo, clave};
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

                new Handler().postDelayed(() -> campo.getBackground().setColorFilter(null), 2000);

                isValido = false;
                break;
            }
        }
        return isValido;
    }

    private void limpiarCampos() {
        correo.setText("");
        clave.setText("");
    }

}