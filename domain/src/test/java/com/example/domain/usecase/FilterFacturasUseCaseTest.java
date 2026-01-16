package com.example.domain.usecase;

import com.example.domain.model.Factura;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterFacturasUseCaseTest {

    private FilterFacturasUseCase useCase;

    @Before
    public void setUp() {
        useCase = new FilterFacturasUseCase();
    }

    @Test
    public void filtrarFacturas_sinFiltros_devuelveTodasLasFacturas() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();
        List<String> estadosSeleccionados = null;

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, estadosSeleccionados, null, null, null, null);

        // Assert
        assertEquals("Debe devolver todas las facturas", 3, resultado.size());
    }

    @Test
    public void filtrarFacturas_conEstadoEspecifico_filtraCorrectamente() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();
        List<String> estadosSeleccionados = List.of("PAGADA");

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, estadosSeleccionados, null, null, null, null);

        // Assert
        assertEquals("Debe devolver solo facturas PAGADA", 1, resultado.size());
        assertEquals("PAGADA", resultado.get(0).getDescEstado());
    }

    @Test
    public void filtrarFacturas_conRangoFechas_filtraCorrectamente() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(
                facturas, null, "01/01/2026", "15/01/2026", null, null
        );

        // Assert (Albacetazo) ← CAMBIA 1 por 2
        assertEquals("Debe devolver facturas en rango de fechas", 2, resultado.size());
    }

    @Test
    public void filtrarFacturas_conRangoImporte_filtraCorrectamente() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();
        Double importeMin = 150.0;
        Double importeMax = 200.0;

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, null, null, null, importeMin, importeMax);

        // Assert
        assertEquals("Debe devolver facturas en rango de importe", 1, resultado.size());
    }

    @Test
    public void filtrarFacturas_conTodosLosFiltros_filtraCorrectamente() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();
        List<String> estadosSeleccionados = List.of("PAGADA");
        Double importeMin = 100.0;
        Double importeMax = 200.0;

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, estadosSeleccionados, null, null, importeMin, importeMax);

        // Assert
        assertEquals("Debe devolver factura que cumpla todos los filtros", 1, resultado.size());
        assertEquals("PAGADA", resultado.get(0).getDescEstado());
    }

    @Test
    public void filtrarFacturas_listaVacia_devuelveListaVacia() {
        // Arrange
        List<Factura> facturas = new ArrayList<>();

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, null, null, null, null, null);

        // Assert
        assertTrue("Debe devolver lista vacía", resultado.isEmpty());
    }

    @Test
    public void filtrarFacturas_estadosVacios_devuelveTodasLasFacturas() {
        // Arrange
        List<Factura> facturas = crearFacturasDePrueba();
        List<String> estadosSeleccionados = new ArrayList<>(); // Lista vacía

        // Act
        List<Factura> resultado = useCase.filtrarFacturas(facturas, estadosSeleccionados, null, null, null, null);

        // Assert
        assertEquals("Lista de estados vacía debe devolver todas las facturas", 3, resultado.size());
    }

    // Metodo de pruebas
    private List<Factura> crearFacturasDePrueba() {
        List<Factura> facturas = new ArrayList<>();

        Factura factura1 = new Factura();
        factura1.setDescEstado("PENDIENTE");
        factura1.setFecha("10/01/2026");
        factura1.setImporteOrdenacion(100.0);
        facturas.add(factura1);

        Factura factura2 = new Factura();
        factura2.setDescEstado("PAGADA");
        factura2.setFecha("12/01/2026");
        factura2.setImporteOrdenacion(175.0);
        facturas.add(factura2);

        Factura factura3 = new Factura();
        factura3.setDescEstado("CANCELADA");
        factura3.setFecha("20/01/2026");
        factura3.setImporteOrdenacion(250.0);
        facturas.add(factura3);

        return facturas;
    }
}
