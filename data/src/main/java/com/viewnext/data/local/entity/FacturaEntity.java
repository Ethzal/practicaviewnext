package com.viewnext.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Representa una factura almacenada en la base de datos local.
 * Esta clase se mapea a la tabla "facturas" de la base de datos utilizando Room.
 * @see FacturaEntity
 */
@Entity(tableName = "facturas")
public class FacturaEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String estado;
    public String fecha;
    public double importe;
}
