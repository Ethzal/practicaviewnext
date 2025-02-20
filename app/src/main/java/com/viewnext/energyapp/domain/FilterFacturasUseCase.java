package com.viewnext.energyapp.domain;

import android.app.Application;

import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.data.repository.GetFacturasRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FilterFacturasUseCase {

    private final GetFacturasRepository getFacturasRepository;

    public FilterFacturasUseCase(Application application) {
        this.getFacturasRepository = new GetFacturasRepository(application);
    }

    public void filtrarFacturas(List<Factura> facturas, List<String> estadosSeleccionados,
                                String fechaInicioString, String fechaFinString,
                                Double importeMin, Double importeMax, FilterFacturasUseCaseCallback callback) {

        // Convertir las fechas de String a Date
        Date fechaInicio = Factura.stringToDate(fechaInicioString);
        Date fechaFin = Factura.stringToDate(fechaFinString);

        // Crear una lista para las facturas filtradas
        List<Factura> facturasFiltradas = new ArrayList<>();

        // Iterar sobre todas las facturas y aplicar los filtros
        for (Factura factura : facturas) {
            boolean cumpleEstado = (estadosSeleccionados == null || estadosSeleccionados.contains(factura.getDescEstado()));
            boolean cumpleFecha = true;

            if (fechaInicio != null && factura.getFecha() != null) {
                cumpleFecha &= Objects.requireNonNull(Factura.stringToDate(factura.getFecha())).compareTo(fechaInicio) >= 0;
            }

            if (fechaFin != null && factura.getFecha() != null) {
                cumpleFecha &= Objects.requireNonNull(Factura.stringToDate(factura.getFecha())).compareTo(fechaFin) <= 0;
            }

            boolean cumpleImporte = (importeMin == null || factura.getImporteOrdenacion() >= importeMin) &&
                    (importeMax == null || factura.getImporteOrdenacion() <= importeMax);

            // Si la factura cumple con todos los filtros, agregarla a la lista filtrada
            if (cumpleEstado && cumpleFecha && cumpleImporte) {
                facturasFiltradas.add(factura);
            }
        }

        // Devolver las facturas filtradas mediante el callback
        callback.onSuccess(facturasFiltradas);
    }
}
