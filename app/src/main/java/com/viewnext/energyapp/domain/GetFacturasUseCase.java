package com.viewnext.energyapp.domain;

import android.app.Application;

import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.data.repository.GetFacturasRepository;
import com.viewnext.energyapp.data.repository.GetFacturasRepositoryCallback;

import java.util.List;

public class GetFacturasUseCase {
    private final GetFacturasRepository getFacturasRepository;

    public GetFacturasUseCase(Application application) {
        this.getFacturasRepository = new GetFacturasRepository(application);
    }

    public void execute(boolean usingRetromock, GetFacturasUseCaseCallback callback) {
        getFacturasRepository.getFacturas(usingRetromock, new GetFacturasRepositoryCallback() {
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
