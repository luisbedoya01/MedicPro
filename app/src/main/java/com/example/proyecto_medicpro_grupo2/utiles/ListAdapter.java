package com.example.proyecto_medicpro_grupo2.utiles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_medicpro_grupo2.MyOpenHelper;
import com.example.proyecto_medicpro_grupo2.R;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<Recordatorio> listRecordatorios;
    private LayoutInflater layoutInflater;

    private Context context;

    public ListAdapter(List<Recordatorio> listRecordatorios, Context context) {
        this.listRecordatorios = listRecordatorios;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        holder.bindData(listRecordatorios.get(position));
    }

    @Override
    public int getItemCount() {
        return listRecordatorios.size();
    }

    public void setItems(List<Recordatorio> items) {
        listRecordatorios = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nombre, fecha, hora, estado;
        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.txtRecordatorioName);
            fecha = itemView.findViewById(R.id.txtRecordatorioDate);
            hora = itemView.findViewById(R.id.txtRecordatorioHour);
            estado = itemView.findViewById(R.id.txtEstado);
        }

        public void bindData(final Recordatorio listRecordatorios) {
            String colorEstado = listRecordatorios.getColorEstado();
            iconImage.setColorFilter(Color.parseColor(colorEstado), PorterDuff.Mode.SRC_IN);
            nombre.setText(listRecordatorios.getNombreMedicamento());
            fecha.setText(listRecordatorios.getFechaRecordatorio());
            hora.setText(listRecordatorios.getHoraRecordatorio());
            estado.setText(listRecordatorios.getDescripcionEstado());
            estado.setTextColor(Color.parseColor(colorEstado));
        }

    }
}
