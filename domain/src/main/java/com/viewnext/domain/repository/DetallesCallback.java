package com.viewnext.domain.repository;

public interface DetallesCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable error);
}