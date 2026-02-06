package com.viewnext.domain.repository;

import com.viewnext.domain.model.Factura;
import java.util.List;

/**
 * Repositorio para obtener facturas desde la fuente de datos (base de datos o remoto).
 * Define los métodos necesarios para recuperar y refrescar las facturas.
 */
public interface GetFacturasRepository {

    /**
     * Obtiene todas las facturas almacenadas en la base de datos local.
     * @return Lista de facturas almacenadas
     */
    List<Factura> getFacturasFromDb();

    // Callback utilizado para notificar el resultado de la operación de refresco de facturas
    interface RepositoryCallback {
        void onSuccess(List<Factura> facturas);
        void onError(String error);
    }

    /**
     * Refresca las facturas desde la fuente de datos, ya sea remoto o mock.
     * @param usingRetromock Si es true, se usa un mock (RetroMock); si es false, se usa la fuente real (Retrofit)
     * @param repositoryCallback Callback para notificar éxito o error
     */
    void refreshFacturas(boolean usingRetromock, RepositoryCallback repositoryCallback);
}
