package com.example.domain.usecase;

import com.example.domain.model.Detalles;
import com.example.domain.repository.GetDetallesRepository;
import java.util.List;

public class GetDetallesUseCase {
    private final GetDetallesRepository repository;

    public GetDetallesUseCase(GetDetallesRepository repository) {
        this.repository = repository;
    }

    public List<Detalles> getDetalles() {
        return repository.getDetalles();
    }

    public void refreshDetalles() {
        repository.refreshDetalles();
    }
}
