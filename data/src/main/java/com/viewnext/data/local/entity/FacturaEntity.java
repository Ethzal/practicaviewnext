package com.viewnext.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "facturas")
public class FacturaEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String estado;
    public String fecha;
    public double importe;
}
