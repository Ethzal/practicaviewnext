package com.example.domain.usecase;

import com.example.domain.model.Factura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FilterFacturasUseCase {

    public FilterFacturasUseCase() {
    }

    public List<Factura> filtrarFacturas(
            List<Factura> facturas,
            List<String> estadosSeleccionados,
            String fechaInicioString,
            String fechaFinString,
            Double importeMin,
            Double importeMax
    ) {

        // Convertir las fechas de String a Date
        Date fechaInicio = Factura.stringToDate(fechaInicioString);
        Date fechaFin = Factura.stringToDate(fechaFinString);

        // Crear una lista para las facturas filtradas
        List<Factura> facturasFiltradas = new ArrayList<>();

        // Iterar sobre todas las facturas y aplicar los filtros
        for (Factura factura : facturas) {
            boolean cumpleEstado = (estadosSeleccionados == null || estadosSeleccionados.isEmpty() || estadosSeleccionados.contains(factura.getDescEstado()));
            boolean cumpleFecha = true;

            if (fechaInicioString != null && !fechaInicioString.isEmpty() && factura.getFecha() != null) {
                if (fechaInicio != null) {
                    cumpleFecha &= Objects.requireNonNull(Factura.stringToDate(factura.getFecha())).compareTo(fechaInicio) >= 0;
                }
            }

            if (fechaFinString != null && !fechaFinString.isEmpty() && factura.getFecha() != null) {
                if (fechaFin != null) {
                    cumpleFecha &= Objects.requireNonNull(Factura.stringToDate(factura.getFecha())).compareTo(fechaFin) <= 0;
                }
            }

            boolean cumpleImporte = true;

            if (importeMin != null) {
                cumpleImporte &= factura.getImporteOrdenacion() >= importeMin;
            }

            if (importeMax != null) {
                cumpleImporte &= factura.getImporteOrdenacion() <= importeMax;
            }

            // Si la factura cumple con todos los filtros, agregarla a la lista filtrada
            if (cumpleEstado && cumpleFecha && cumpleImporte) {
                facturasFiltradas.add(factura);
            }
        }

        return facturasFiltradas;
    }
}
