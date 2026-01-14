package com.example.domain.model;

public class Detalles {
    private String cau;
    private String estadoSolicitud;
    private String tipoAutoconsumo;
    private String compensacion;
    private String potencia;

    // Constructor
    public Detalles(String cau, String estadoSolicitud, String tipoAutoconsumo, String compensacion, String potencia) {
        this.cau = cau;
        this.estadoSolicitud = estadoSolicitud;
        this.tipoAutoconsumo = tipoAutoconsumo;
        this.compensacion = compensacion;
        this.potencia = potencia;
    }

    // Getters y Setters
    public String getCau() {
        return cau;
    }

    public void setCau(String cau) {
        this.cau = cau;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getTipoAutoconsumo() {
        return tipoAutoconsumo;
    }

    public void setTipoAutoconsumo(String tipoAutoconsumo) {
        this.tipoAutoconsumo = tipoAutoconsumo;
    }

    public String getCompensacion() {
        return compensacion;
    }

    public void setCompensacion(String compensacion) {
        this.compensacion = compensacion;
    }

    public String getPotencia() {
        return potencia;
    }

    public void setPotencia(String potencia) {
        this.potencia = potencia;
    }
}
