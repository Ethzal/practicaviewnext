package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Detalles;
import com.viewnext.domain.repository.DetallesCallback;
import com.viewnext.domain.repository.GetDetallesRepository;

import java.util.List;


public class GetDetallesUseCase {
    private final GetDetallesRepository repository;

    public GetDetallesUseCase(GetDetallesRepository repository) {
        this.repository = repository;
    }

    public void refreshDetalles(DetallesCallback<List<Detalles>> callback) {
        repository.refreshDetalles(new DetallesCallback<>() {
            @Override
            public void onSuccess(List<Detalles> detalles) {
                callback.onSuccess(detalles);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}