package com.viewnext.domain.usecase;

import com.viewnext.domain.repository.GetDetallesRepository;

public class GetDetallesUseCase {
    private final GetDetallesRepository repository;

    public GetDetallesUseCase(GetDetallesRepository repository) {
        this.repository = repository;
    }

    public void refreshDetalles() {
        repository.refreshDetalles();
    }
}
