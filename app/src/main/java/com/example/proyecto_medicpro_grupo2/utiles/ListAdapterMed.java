package com.example.proyecto_medicpro_grupo2.utiles;

import android.content.Context;
import android.content.SharedPreferences;
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

public class ListAdapterMed extends RecyclerView.Adapter<ListAdapterMed.ViewHolder>{
    private List<Medicamento> listMedicamentos;
    private LayoutInflater layoutInflater;

    private Context context;

    private int idUsuario;

    private MyOpenHelper dbMedicPro;

    private SharedPreferences preferences;

    public ListAdapterMed(List<Medicamento> listMedicamentos, Context context) {
        this.listMedicamentos = listMedicamentos;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapterMed.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_element_med, null);
        return new ListAdapterMed.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterMed.ViewHolder holder, int position) {
        holder.bindData(listMedicamentos.get(position));
    }

    @Override
    public int getItemCount() {
        return listMedicamentos.size();
    }

    public void setItems(List<Medicamento> items) {
        listMedicamentos = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView nombre, dosis, recomendacion, porcentaje;
        ViewHolder(View itemView) {
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            nombre = itemView.findViewById(R.id.txtMedName);
            dosis = itemView.findViewById(R.id.txtMedDosis);
            porcentaje = itemView.findViewById(R.id.txtPorcentaje);
            recomendacion = itemView.findViewById(R.id.txtRecomendacionDesc);
        }

        public void bindData(final Medicamento item) {
            nombre.setText(item.getNombre());
            dosis.setText("Dosis: " + item.getDosis());
            porcentaje.setText(item.getPorcentajeMedicamento() + "%");
            int porcentaje = item.getPorcentajeMedicamento();
            if (porcentaje >= 50) {
                recomendacion.setText("Cumplimiento adecuado del trtamiento");
            } else {
                recomendacion.setText("Mejorar la adherencia al tratamiento");
            }
        }
    }
}
