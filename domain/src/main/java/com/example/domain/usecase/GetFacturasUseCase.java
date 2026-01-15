package com.example.domain.usecase;

import com.example.domain.repository.GetFacturasRepository;


public class GetFacturasUseCase {

    private final GetFacturasRepository repository;

    public GetFacturasUseCase(GetFacturasRepository repository) {
        this.repository = repository;
    }

    public void refreshFacturas(boolean usingRetromock) {
        repository.refreshFacturas(usingRetromock);
    }
}
