package com.viewnext.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Representa una factura almacenada en la base de datos local.
 * Esta clase se mapea a la tabla "facturas" de la base de datos utilizando Room.
 * @see FacturaEntity
 */
@Entity(tableName = "facturas")
class FacturaEntity {
    @JvmField
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @JvmField
    var estado: String? = null
    @JvmField
    var fecha: String? = null
    @JvmField
    var importe: Double = 0.0
}
