package com.viewnext.domain.repository;

import com.viewnext.domain.model.Factura;
import java.util.List;

public interface GetFacturasRepository {

//    List<Factura> getFacturasFromDb();

    void refreshFacturas(boolean usingRetromock);
}
