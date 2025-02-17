package com.viewnext.energyapp.data.model;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Factura {
    private String descEstado;
    private double importeOrdenacion;
    private String fecha;

    // Constructor
    public Factura(String descEstado, double importeOrdenacion, String fecha) {
        this.descEstado = descEstado;
        this.importeOrdenacion = importeOrdenacion;
        this.fecha = fecha;
    }

    // Getters y setters
    public String getDescEstado() {
        return descEstado;
    }

    public void setDescEstado(String descEstado) {
        this.descEstado = descEstado;
    }

    public double getImporteOrdenacion() {
        return importeOrdenacion;
    }

    public void setImporteOrdenacion(double importeOrdenacion) {
        this.importeOrdenacion = importeOrdenacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public static Date stringToDate(String fechaString) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  // Ajusta el formato de acuerdo a tu fecha
        try {
            return sdf.parse(fechaString);  // Convierte el String a Date
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

