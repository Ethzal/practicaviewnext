package com.viewnext.domain.model;

import java.util.List;

public class DetallesResponse { // Modelar los datos de la respuesta de la API
    List<Detalles> detalles;

    // Get y Set
    public List<Detalles> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalles> detalles) {
        this.detalles = detalles;
    }
}
