package com.example.proyecto_medicpro_grupo2.utiles;

public class Recordatorio {
    private int idRecordatorio;
    private String nombreRecordatorio;

    private String horaRecordatorio;

    private String fechaRecordatorio;

    private int idMedicamento;

    private int idUsuario;

    private int idEstado;

    private String colorEstado;

    private String descripcionEstado;

    public String getColorEstado() {
        return colorEstado;
    }

    public void setColorEstado(String colorEstado) {
        this.colorEstado = colorEstado;
    }

    public String getDescripcionEstado() {
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    private String nombreMedicamento;

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public Recordatorio() {
    }

    public Recordatorio(String nombreRecordatorio, String horaRecordatorio, String fechaRecordatorio, int idMedicamento, int idUsuario, int idEstado) {
        this.nombreRecordatorio = nombreRecordatorio;
        this.horaRecordatorio = horaRecordatorio;
        this.fechaRecordatorio = fechaRecordatorio;
        this.idMedicamento = idMedicamento;
        this.idUsuario = idUsuario;
        this.idEstado = idEstado;
    }

    public Recordatorio(String nombreRecordatorio, String horaRecordatorio, String fechaRecordatorio, int idMedicamento, int idUsuario, int idEstado, String colorEstado) {
        this.nombreRecordatorio = nombreRecordatorio;
        this.horaRecordatorio = horaRecordatorio;
        this.fechaRecordatorio = fechaRecordatorio;
        this.idMedicamento = idMedicamento;
        this.idUsuario = idUsuario;
        this.idEstado = idEstado;
        this.colorEstado = colorEstado;
    }

    public Recordatorio(int idRecordatorio, String nombreRecordatorio, String horaRecordatorio, String fechaRecordatorio, int idMedicamento) {
        this.idRecordatorio = idRecordatorio;
        this.nombreRecordatorio = nombreRecordatorio;
        this.horaRecordatorio = horaRecordatorio;
        this.fechaRecordatorio = fechaRecordatorio;
        this.idMedicamento = idMedicamento;
    }

    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    public String getNombreRecordatorio() {
        return nombreRecordatorio;
    }

    public void setNombreRecordatorio(String nombreRecordatorio) {
        this.nombreRecordatorio = nombreRecordatorio;
    }

    public String getHoraRecordatorio() {
        return horaRecordatorio;
    }

    public void setHoraRecordatorio(String horaRecordatorio) {
        this.horaRecordatorio = horaRecordatorio;
    }

    public String getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(String fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String generarNombreRecordatorio(int numeroRecordatorio) {
        return "Recordatorio N° " + (numeroRecordatorio + 1);
    }


}
