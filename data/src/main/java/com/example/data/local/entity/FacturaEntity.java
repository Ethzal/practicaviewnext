package com.example.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "facturas")
public class FacturaEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;
    public String estado;
    public String fecha;
    public double importe;
}
