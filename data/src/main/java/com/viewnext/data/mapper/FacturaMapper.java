package com.viewnext.data.mapper;

import com.viewnext.data.local.entity.FacturaEntity;
import com.viewnext.domain.model.Factura;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Mapper que se encarga de transformar los objetos entre el modelo de dominio {@link Factura} y
 * la entidad de la base de datos {@link FacturaEntity}. Los métodos de esta clase permiten convertir
 * entre estas representaciones, tanto a nivel individual como en listas completas.
 */
public class FacturaMapper {
    /**
     * Convierte un objeto {@link Factura} en su correspondiente entidad {@link FacturaEntity}.
     * @param factura El objeto de dominio {@link Factura} a convertir.
     * @return La entidad {@link FacturaEntity} que representa la factura en la base de datos.
     */
    public static FacturaEntity toEntity(Factura factura) {
        FacturaEntity entity = new FacturaEntity();
        entity.estado = factura.getDescEstado();
        entity.fecha = factura.getFecha();
        entity.importe = factura.getImporteOrdenacion();
        entity.id = (factura.getDescEstado() + factura.getFecha() + factura.getImporteOrdenacion())
                .hashCode();
        return entity;
    }

    /**
     * Convierte una lista de objetos {@link Factura} en una lista de entidades {@link FacturaEntity}.
     * @param facturas La lista de objetos {@link Factura} a convertir.
     * @return La lista de entidades {@link FacturaEntity} correspondientes.
     */
    public static List<FacturaEntity> toEntityList(List<Factura> facturas) {
        List<FacturaEntity> entities = new ArrayList<>();
        for (Factura factura : facturas) {
            entities.add(toEntity(factura));
        }
        return entities;
    }

    /**
     * Convierte una entidad {@link FacturaEntity} a su correspondiente objeto de dominio {@link Factura}.
     * @param entity La entidad {@link FacturaEntity} a convertir.
     * @return El objeto de dominio {@link Factura} correspondiente.
     */
    public static Factura toDomain(FacturaEntity entity) {
        Factura factura = new Factura();
        factura.setDescEstado(entity.estado);
        factura.setFecha(entity.fecha);
        factura.setImporteOrdenacion(entity.importe);
        return factura;
    }

    /**
     * Convierte una lista de entidades {@link FacturaEntity} en una lista de objetos de dominio {@link Factura}.
     * @param entities La lista de entidades {@link FacturaEntity} a convertir.
     * @return La lista de objetos de dominio {@link Factura} correspondientes.
     */
    public static List<Factura> toDomainList(List<FacturaEntity> entities) {
        List<Factura> facturas = new ArrayList<>();
        for (FacturaEntity entity : entities) {
            facturas.add(toDomain(entity));
        }
        return facturas;
    }

}
