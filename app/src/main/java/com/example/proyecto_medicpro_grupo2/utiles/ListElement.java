package com.example.proyecto_medicpro_grupo2.utiles;

public class ListElement {
    private String nombreMedicamento;
    private String fecha;
    private String hora;

    private String estado;

    private String color;

    public ListElement() {
    }

    public ListElement(String nombreMedicamento, String fecha, String hora, String estado, String color) {
        this.nombreMedicamento = nombreMedicamento;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
