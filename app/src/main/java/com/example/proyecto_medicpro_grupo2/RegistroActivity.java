package com.example.proyecto_medicpro_grupo2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistroActivity extends AppCompatActivity {

    EditText nombre, correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nombre = findViewById(R.id.txtNombresApellidos);
        correo = findViewById(R.id.txtCorreo);
    }

    public void enviarCorreo (View view) {
        final String userName = "medicproapp@gmail.com";
        final String password = "hxivjlapkvfdwpcq";

        final String destinatario = correo.getText().toString();
        final String asunto = "Bienvenido a MedicPro";
        final String mensaje = "Hola " + nombre.getText().toString() + ",\n\n" +
                "¡Bienvenido a MedicPro! Gracias por registrarte en nuestra aplicación.\n\n" +
                "Atentamente,\n" +
                "Equipo de MedicPro";

        if (!destinatario.isEmpty()) {
            new SendMailTask().execute(userName, password, destinatario, asunto, mensaje);
        } else {
            Toast.makeText(RegistroActivity.this, "Por favor, ingrese un correo electrónico", Toast.LENGTH_LONG).show();
        }
    }

    private class SendMailTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            //Toast.makeText(RegistroActivity.this, params[0] + " " + params[1], Toast.LENGTH_LONG).show();
            try {
                MailSender sender = new MailSender(params[0], params[1]);
                sender.enviarEmail(params[2], params[3], params[4]);
                return "Correo enviado con éxito";
            } catch (Exception e) {
                //e.printStackTrace();
                return "Error al enviar el correo: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(RegistroActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

}