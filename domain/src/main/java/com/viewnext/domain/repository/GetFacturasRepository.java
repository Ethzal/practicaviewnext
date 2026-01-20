package com.viewnext.domain.repository;

import com.viewnext.domain.model.Factura;
import java.util.List;

public interface GetFacturasRepository {

    List<Factura> getFacturasFromDb();

    interface RepositoryCallback {
        void onSuccess(List<Factura> facturas);
        void onError(String error);
    }

    void refreshFacturas(boolean usingRetromock, RepositoryCallback repositoryCallback);
}
