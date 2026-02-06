package com.viewnext.domain.repository;

import com.viewnext.domain.model.Detalles;
import java.util.List;

/**
 * Repositorio para obtener los detalles de la instalación o entidad correspondiente.
 * Define los métodos para recuperar y refrescar los detalles desde la fuente de datos.
 */
public interface GetDetallesRepository {
    /**
     * Obtiene los detalles almacenados localmente.
     * @return Lista de detalles
     */
    List<Detalles> getDetalles();

    /**
     * Refresca los detalles desde la fuente de datos.
     * @param detallesCallback Callback que será notificado cuando la operación haya finalizado.
     */
    void refreshDetalles(DetallesCallback<List<Detalles>> detallesCallback);
}
