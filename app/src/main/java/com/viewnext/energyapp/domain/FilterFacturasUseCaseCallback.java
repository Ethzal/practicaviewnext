package com.viewnext.energyapp.domain;

import com.viewnext.energyapp.data.model.Factura;

import java.util.List;

public interface FilterFacturasUseCaseCallback {
    void onSuccess(List<Factura> facturasFiltradas);
    void onError(String error);
}
