package com.viewnext.domain.repository

import com.viewnext.domain.model.Detalles

/**
 * Repositorio para obtener los detalles de la instalación o entidad correspondiente.
 * Define los métodos para recuperar y refrescar los detalles desde la fuente de datos.
 */
interface GetDetallesRepository {
    /**
     * Obtiene los detalles almacenados localmente.
     * @return Lista de detalles
     */
    val detalles: MutableList<Detalles?>?

    /**
     * Refresca los detalles desde la fuente de datos.
     * @param detallesCallback Callback que será notificado cuando la operación haya finalizado.
     */
    fun refreshDetalles(detallesCallback: DetallesCallback<MutableList<Detalles?>?>?)
}
