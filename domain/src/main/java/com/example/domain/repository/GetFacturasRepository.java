package com.example.domain.repository;

import com.example.domain.model.Factura;
import java.util.List;

public interface GetFacturasRepository {

    List<Factura> getFacturasFromDb();

    void refreshFacturas(boolean usingRetromock);
}
