package com.example.proyecto_medicpro_grupo2.utiles;

public class Medicamento {
    private int idMedicamento;
    private String nombre;
    private String dosis;
    private int idUsuario;
    private int porcentajeMedicamento;

    private String recomendacion;

    public Medicamento() {

    }

    public Medicamento(String nombre, String dosis, int idUsuario) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.idUsuario = idUsuario;
    }

    public Medicamento(int idMedicamento, String nombreMedicamento) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombreMedicamento;
    }

    public Medicamento(int idMedicamento, String nombre, String dosis, int idUsuario) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.dosis = dosis;
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getPorcentajeMedicamento() {
        return porcentajeMedicamento;
    }

    public void setPorcentajeMedicamento(int porcentajeMedicamento) {
        this.porcentajeMedicamento = porcentajeMedicamento;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    @Override
    public String toString() {
        return nombre;
    }


}
