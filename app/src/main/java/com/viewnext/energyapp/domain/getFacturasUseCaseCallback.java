package com.viewnext.energyapp.domain;

import com.viewnext.energyapp.data.model.Factura;

import java.util.List;

public interface getFacturasUseCaseCallback {
    void onSuccess(List<Factura> facturas);
    void onError(String error);
}
