package com.viewnext.domain.usecase;

import com.viewnext.domain.repository.GetFacturasRepository;


public class GetFacturasUseCase {

    private final GetFacturasRepository repository;

    public GetFacturasUseCase(GetFacturasRepository repository) {
        this.repository = repository;
    }

    public void refreshFacturas(boolean usingRetromock) {
        repository.refreshFacturas(usingRetromock);
    }
}
