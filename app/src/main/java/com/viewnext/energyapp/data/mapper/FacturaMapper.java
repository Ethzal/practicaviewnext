package com.viewnext.energyapp.data.mapper;

import com.viewnext.energyapp.data.local.entity.FacturaEntity;
import com.viewnext.energyapp.data.model.Factura;

import java.util.ArrayList;
import java.util.List;

public class FacturaMapper {
    public static FacturaEntity toEntity(Factura factura) {
        FacturaEntity entity = new FacturaEntity();
        entity.estado = factura.getDescEstado();
        entity.fecha = factura.getFecha();
        entity.importe = factura.getImporteOrdenacion();
        entity.id = (factura.getDescEstado() + factura.getFecha() + factura.getImporteOrdenacion())
                .hashCode();
        return entity;
    }

    public static List<FacturaEntity> toEntityList(List<Factura> facturas) {
        List<FacturaEntity> entities = new ArrayList<>();
        for (Factura factura : facturas) {
            entities.add(toEntity(factura));
        }
        return entities;
    }

    public static Factura toDomain(FacturaEntity entity) {
        Factura factura = new Factura();
        //factura.setId(entity.id);
        factura.setDescEstado(entity.estado);
        factura.setFecha(entity.fecha);
        factura.setImporteOrdenacion(entity.importe);
        return factura;
    }

    public static List<Factura> toDomainList(List<FacturaEntity> entities) {
        List<Factura> facturas = new ArrayList<>();
        for (FacturaEntity entity : entities) {
            facturas.add(toDomain(entity));
        }
        return facturas;
    }

}
