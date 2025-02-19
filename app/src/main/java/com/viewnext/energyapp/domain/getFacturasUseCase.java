package com.viewnext.energyapp.domain;

import android.app.Application;

import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.data.repository.FacturaRepository;
import com.viewnext.energyapp.data.repository.FacturaRepositoryCallback;

import java.util.List;

public class getFacturasUseCase {
    private final FacturaRepository facturaRepository;

    public getFacturasUseCase(Application application) {
        this.facturaRepository = new FacturaRepository(application);
    }

    public void execute(boolean usingRetromock, getFacturasUseCaseCallback callback) {
        facturaRepository.getFacturas(usingRetromock, new FacturaRepositoryCallback() {
            @Override
            public void onSuccess(List<Factura> facturas) {
                callback.onSuccess(facturas);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        });
    }
}
