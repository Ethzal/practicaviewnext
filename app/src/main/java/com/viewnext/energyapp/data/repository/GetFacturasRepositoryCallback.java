package com.viewnext.energyapp.data.repository;

import com.viewnext.energyapp.data.model.Factura;

import java.util.List;

public interface GetFacturasRepositoryCallback {
    void onSuccess(List<Factura> facturas);
    void onError(String error);
}
