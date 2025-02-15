package com.viewnext.energyapp.data.model;

import java.util.List;

public class FacturaResponse { // Modelar los datos de la respuesta de la API
    private int numFacturas;
    private List<Factura> facturas;

    // Getters y Setters
    public int getNumFacturas() {
        return numFacturas;
    }

    public void setNumFacturas(int numFacturas) {
        this.numFacturas = numFacturas;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }
}
