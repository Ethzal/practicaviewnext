package com.viewnext.domain.repository;

/**
 * Callback genérico para operaciones asincrónicas que retornan un resultado o un error.
 * @param <T> Tipo de resultado esperado en caso de éxito.
 */
public interface DetallesCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable error);
}