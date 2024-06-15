package com.example.proyecto_medicpro_grupo2.utiles;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyecto_medicpro_grupo2.R;

public class Utils {

    public static void showCustomToast(Context context, String mensaje, int layoutId, int textViewId, int containerId, int duration) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View layout = layoutInflater.inflate(layoutId, null);
        TextView textView = layout.findViewById(textViewId);
        textView.setText(mensaje);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM, 0, 200);
        toast.setDuration(duration);
        toast.setView(layout);
        toast.show();
    }

    public static void toastOK(Context context, String mensaje) {
        showCustomToast(context, mensaje, R.layout.custom_toast_ok, R.id.txtMensajeToastOk, R.id.ll_custom_toast_ok, Toast.LENGTH_LONG);
    }

    public static void toastError(Context context, String mensaje) {
        showCustomToast(context, mensaje, R.layout.custom_toast_error, R.id.txtMensajeToastError, R.id.ll_custom_toast_error, Toast.LENGTH_LONG);
    }

    public static void toastVacio(Context context, String mensaje) {
        showCustomToast(context, mensaje, R.layout.custom_toast_vacio, R.id.txtMensajeToastVacio, R.id.ll_custom_toast_vacio, Toast.LENGTH_LONG);
    }
}
