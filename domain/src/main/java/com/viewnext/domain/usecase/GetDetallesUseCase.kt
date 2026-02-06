package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Detalles;
import com.viewnext.domain.repository.DetallesCallback;
import com.viewnext.domain.repository.GetDetallesRepository;

import java.util.List;

/**
 * Caso de uso para obtener los detalles de la instalación desde el repositorio.
 * Encapsula la lógica de negocio para la recuperación de detalles.
 */
public class GetDetallesUseCase {
    private final GetDetallesRepository repository;

    /**
     * Constructor del caso de uso.
     * @param repository Repositorio que maneja la obtención de detalles
     */
    public GetDetallesUseCase(GetDetallesRepository repository) {
        this.repository = repository;
    }

    /**
     * Ejecuta la actualización de los detalles.
     * @param callback Callback que será notificado con los resultados
     */
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