package com.example.proyecto_medicpro_grupo2;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_medicpro_grupo2.utiles.Usuario;
import com.example.proyecto_medicpro_grupo2.utiles.Utils;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarUsuarioActivity extends AppCompatActivity {

    EditText nombresApellidos, correo, clave;
    Button btnGuardarRegistro, btnLimpiarRegistro, btnVolverLogin;

    FirebaseAuth firebaseAuth;

    ProgressDialog progressDialog;

    String nombre = "" , correoElectronico = "" , contrasenia = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombresApellidos = findViewById(R.id.edtNombresApellidos);
        correo = findViewById(R.id.edtCorreoElectronico);
        clave = findViewById(R.id.edtContrasenia);
        btnGuardarRegistro = findViewById(R.id.btnGuardarUsuario);
        btnLimpiarRegistro = findViewById(R.id.btnLimpiarUsuario);
        btnVolverLogin = findViewById(R.id.btnCancelar);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere, por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        btnGuardarRegistro.setOnClickListener(v -> guardarRegistro(v));
        //btnGuardarRegistro.setOnClickListener(v -> validarDatos(v));
        btnLimpiarRegistro.setOnClickListener(v -> limpiarCampos(v));
        btnVolverLogin.setOnClickListener(v -> volver(v));
    }

    public void guardarRegistro (View view) {
        if (verificarCampos()) {
            String nombreUsuario = nombresApellidos.getText().toString();
            String correoUsuario = correo.getText().toString();
            String claveUsuario = clave.getText().toString();

            Usuario usuario = new Usuario(nombreUsuario, correoUsuario, claveUsuario);

            guardarBD(usuario);

            //guardarBD(nombresApellidos.getText().toString(), correo.getText().toString(), clave.getText().toString());
            Utils.toastOK(this, "Registro exitoso");
            enviarCorreo(correo.getText().toString(), nombresApellidos.getText().toString());
            volver(view);
        } else {
            Utils.toastVacio(this, "Por favor, complete todos los campos");
        }

    }

    //Método prueba//
    /*private void validarDatos(View view) {
        nombre = nombresApellidos.getText().toString();
        correoElectronico = correo.getText().toString();
        contrasenia = clave.getText().toString();

        if (nombre.isEmpty()) {
            nombresApellidos.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            new Handler().postDelayed(() -> nombresApellidos.getBackground().setColorFilter(null), 2000);
            Utils.toastVacio(this, "Debe ingresar su nombre y apellidos");

        } else if (!Patterns.EMAIL_ADDRESS.matcher(correoElectronico).matches()) {
            correo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            new Handler().postDelayed(() -> correo.getBackground().setColorFilter(null), 2000);
            Utils.toastError(this, "Ingrese un correo válido");
        } else if (contrasenia.isEmpty()) {
            clave.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            new Handler().postDelayed(() -> clave.getBackground().setColorFilter(null), 2000);
            Utils.toastVacio(this, "Debe ingresar una contraseña");
        } else  {
            crearCuenta();
        }
    }*/

    //Método prueba//
    /*private void crearCuenta() {
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(correoElectronico, contrasenia)
                .addOnSuccessListener(authResult -> guardarInfo()).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Utils.toastError(RegistrarUsuarioActivity.this, "Error al crear la cuenta: " + e.getMessage());
                });


    }

    //Método prueba//
    private void guardarInfo() {
        progressDialog.setMessage("Guardando su información...");
        progressDialog.dismiss();

        String uid = firebaseAuth.getUid();

        HashMap<String, String> datosUsuario = new HashMap<>();
        datosUsuario.put("uId", uid);
        datosUsuario.put("nombre", nombre);
        datosUsuario.put("correo", correoElectronico);
        datosUsuario.put("clave", contrasenia);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Usuarios");
        dbRef.child(uid).
                setValue(datosUsuario)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    Utils.toastOK(RegistrarUsuarioActivity.this, "Cuenta creada con éxito");
                    startActivity(new Intent(RegistrarUsuarioActivity.this, inicio.class));
                }).addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Utils.toastError(RegistrarUsuarioActivity.this, "Error al guardar la información: " + e.getMessage());
                });
    }*/

    private boolean verificarCampos () {
        boolean isValido = true;

        EditText[] campos = {nombresApellidos, correo, clave};
        for (EditText campo : campos) {
            if (campo.getText().toString().isEmpty()) {
                campo.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);

                new Handler().postDelayed(() -> campo.getBackground().setColorFilter(null), 2000);

                isValido = false;
                break;
            }
        }
        return isValido;
    }

    private void guardarBD(String nombresApellidos, String correo, String clave) {
        MyOpenHelper dbMedicPro = new MyOpenHelper(this);
        final SQLiteDatabase dbMedicProMode = dbMedicPro.getWritableDatabase();

        if (dbMedicProMode != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombresApellidos", nombresApellidos);
            contentValues.put("correo", correo);
            contentValues.put("clave", clave);

            dbMedicProMode.insert("Usuario", null, contentValues);

        }

    }

    private void guardarBD (Usuario usuario) {
        MyOpenHelper dbMedicPro = new MyOpenHelper(this);
        final SQLiteDatabase dbMedicProMode = dbMedicPro.getWritableDatabase();

        if (dbMedicProMode != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombresApellidos", usuario.getNombresApellidos());
            contentValues.put("correo", usuario.getCorreo());
            contentValues.put("clave", usuario.getClave());

            dbMedicProMode.insert("Usuario", null, contentValues);
        }
    }

    private void enviarCorreo (String destinatario, String nombresApellidos) {
        final String userName = "medicproapp@gmail.com";
        final String password = "hxivjlapkvfdwpcq";

        final String asunto = "Bienvenido a MedicPro";
        final String mensaje = "Hola " + nombresApellidos + ",\n\n" +
                "¡Bienvenido a MedicPro! Gracias por registrarte en nuestra aplicación.\n\n" +
                "Atentamente,\n" +
                "Equipo de MedicPro";
        new SendMailTask().execute(userName, password, destinatario, asunto, mensaje);

    }

    private void limpiarCampos(View view) {
        nombresApellidos.setText("");
        correo.setText("");
        clave.setText("");
    }

    public void volver (View view) {
        Intent ventanaLogin = new Intent(this, LoginActivity.class);
        startActivity(ventanaLogin);
    }


    private class SendMailTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                MailSender sender = new MailSender(params[0], params[1]);
                sender.enviarEmail(params[2], params[3], params[4]);
                //return "Correo enviado con éxito";
            } catch (Exception e) {
                //return "Error al enviar el correo: " + e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(RegistrarUsuarioActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}