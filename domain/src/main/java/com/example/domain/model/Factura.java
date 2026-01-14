package com.example.domain.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

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
        if (fechaString == null || "día/mes/año".equals(fechaString)) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(fechaString);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Factura)) return false;
        Factura factura = (Factura) o;
        return Double.compare(factura.importeOrdenacion, importeOrdenacion) == 0 &&
                Objects.equals(descEstado, factura.descEstado) &&
                Objects.equals(fecha, factura.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descEstado, importeOrdenacion, fecha);
    }
}

