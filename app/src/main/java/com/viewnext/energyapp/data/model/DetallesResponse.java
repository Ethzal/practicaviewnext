package com.viewnext.energyapp.data.model;

import java.util.List;

public class DetallesResponse {
    List<Detalles> detalles;

    // Get y Set
    public List<Detalles> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalles> detalles) {
        this.detalles = detalles;
    }
}
