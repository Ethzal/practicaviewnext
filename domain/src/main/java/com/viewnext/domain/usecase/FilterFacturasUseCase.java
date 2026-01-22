package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Factura;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Caso de uso que filtra una lista de facturas según criterios de estado, fecha e importe.
 * Encapsula la lógica de filtrado utilizada en la presentación de facturas.
 */
public class FilterFacturasUseCase {

    public FilterFacturasUseCase() {
        // Constructor vacío
    }

    /**
     * Filtra las facturas según los criterios proporcionados.
     * @param facturas           Lista completa de facturas a filtrar
     * @param estadosSeleccionados Lista de estados a incluir; si es nula o vacía, no filtra por estado
     * @param fechaInicioString  Fecha de inicio en formato String ("dd/MM/yyyy"); puede ser nula o vacía
     * @param fechaFinString     Fecha de fin en formato String ("dd/MM/yyyy"); puede ser nula o vacía
     * @param importeMin         Importe mínimo a incluir; puede ser nulo
     * @param importeMax         Importe máximo a incluir; puede ser nulo
     * @return Lista de facturas que cumplen con todos los filtros
     */
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
