package com.example.domain.usecase;

import com.example.domain.model.Factura;
import com.example.domain.repository.GetFacturasRepository;

import java.util.List;

public class GetFacturasUseCase {

    private final GetFacturasRepository repository;

    public GetFacturasUseCase(GetFacturasRepository repository) {
        this.repository = repository;
    }

    public List<Factura> getFacturasFromDb() {
        return repository.getFacturasFromDb();
    }

    public void refreshFacturas(boolean usingRetromock) {
        repository.refreshFacturas(usingRetromock);
    }
}
