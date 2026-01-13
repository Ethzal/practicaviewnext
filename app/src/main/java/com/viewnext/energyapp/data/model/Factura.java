package com.viewnext.energyapp.data.model;

import android.annotation.SuppressLint;
import android.util.Log;

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

    public Factura(){
        // Constructor vacio
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            if (fechaString.equals("día/mes/año")) {
                return null;
            }
            return sdf.parse(fechaString);  // Convierte el String a Date
        } catch (ParseException e) {
            Log.e("FechaParseError", "Error al convertir la fecha: " + fechaString, e);
            return null;
        }
    }
}

