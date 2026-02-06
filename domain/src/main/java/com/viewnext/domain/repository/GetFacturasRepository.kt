package com.viewnext.domain.repository

import com.viewnext.domain.model.Factura

/**
 * Repositorio para obtener facturas desde la fuente de datos (base de datos o remoto).
 * Define los métodos necesarios para recuperar y refrescar las facturas.
 */
interface GetFacturasRepository {
    /**
     * Obtiene todas las facturas almacenadas en la base de datos local.
     * @return Lista de facturas almacenadas
     */
    val facturasFromDb: MutableList<Factura?>?

    // Callback utilizado para notificar el resultado de la operación de refresco de facturas
    interface RepositoryCallback {
        fun onSuccess(facturas: MutableList<Factura?>?)
        fun onError(error: String?)
    }

    /**
     * Refresca las facturas desde la fuente de datos, ya sea remoto o mock.
     * @param usingRetromock Si es true, se usa un mock (RetroMock); si es false, se usa la fuente real (Retrofit)
     * @param repositoryCallback Callback para notificar éxito o error
     */
    fun refreshFacturas(usingRetromock: Boolean, repositoryCallback: RepositoryCallback?)
}
