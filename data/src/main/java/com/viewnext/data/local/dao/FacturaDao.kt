package com.viewnext.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.viewnext.data.local.entity.FacturaEntity;

import java.util.List;

/**
 * Interfaz de acceso a los datos de la tabla "facturas" en la base de datos.
 * Utiliza Room para facilitar las operaciones CRUD (Crear, Leer, Actualizar, Eliminar).
 */
@Dao
public interface FacturaDao {
    /**
     * Obtiene todas las facturas de la base de datos de manera asíncrona.
     * El resultado es devuelto como un {@link LiveData}, lo que permite
     * que cualquier cambio en la base de datos sea observado en tiempo real.
     * @return {@link LiveData} que contiene una lista de todas las facturas en la base de datos.
     */
    @Query("SELECT * FROM facturas")
    LiveData<List<FacturaEntity>> getFacturas();

    /**
     * Obtiene todas las facturas de la base de datos de manera síncrona.
     * Devuelve la lista completa de facturas sin observar cambios en tiempo real.
     * @return Lista de facturas almacenadas en la base de datos.
     */
    @Query("SELECT * FROM facturas")
    List<FacturaEntity> getFacturasDirect();

    /**
     * Inserta un conjunto de facturas en la base de datos.
     * Si alguna factura ya existe (mismo id), será reemplazada.
     * @param facturas Lista de facturas a insertar o reemplazar.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FacturaEntity> facturas);

    /**
     * Elimina todas las facturas de la base de datos.
     * Vacía la tabla "facturas".
     */
    @Query("DELETE FROM facturas")
    void deleteAll();
}
