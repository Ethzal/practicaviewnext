package com.example.domain.usecase;

import com.example.domain.repository.GetDetallesRepository;

public class GetDetallesUseCase {
    private final GetDetallesRepository repository;

    public GetDetallesUseCase(GetDetallesRepository repository) {
        this.repository = repository;
    }

    public void refreshDetalles() {
        repository.refreshDetalles();
    }
}
