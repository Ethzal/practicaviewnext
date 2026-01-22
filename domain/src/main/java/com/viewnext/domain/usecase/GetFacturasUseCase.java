package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Factura;
import com.viewnext.domain.repository.GetFacturasRepository;
import java.util.List;


public class GetFacturasUseCase {

    private final GetFacturasRepository repository;

    public GetFacturasUseCase(GetFacturasRepository repository) {
        this.repository = repository;
    }

    public interface Callback {
        void onSuccess(List<Factura> facturas);
        void onError(String error);
    }

    public void execute(boolean usingRetromock, Callback callback) {
        repository.refreshFacturas(usingRetromock, new GetFacturasRepository.RepositoryCallback() {
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
