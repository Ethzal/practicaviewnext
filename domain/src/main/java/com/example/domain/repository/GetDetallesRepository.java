package com.example.domain.repository;

import com.example.domain.model.Detalles;
import java.util.List;

public interface GetDetallesRepository {
    List<Detalles> getDetalles();
    void refreshDetalles();
}
