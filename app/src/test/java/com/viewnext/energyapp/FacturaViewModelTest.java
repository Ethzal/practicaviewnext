package com.viewnext.energyapp;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.viewnext.energyapp.data.model.Factura;
import com.viewnext.energyapp.domain.FilterFacturasUseCase;
import com.viewnext.energyapp.domain.GetFacturasUseCase;
import com.viewnext.energyapp.domain.GetFacturasUseCaseCallback;
import com.viewnext.energyapp.domain.FilterFacturasUseCaseCallback;
import com.viewnext.energyapp.presentation.viewmodel.FacturaViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

public class FacturaViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private GetFacturasUseCase getFacturasUseCase;

    @Mock
    private FilterFacturasUseCase filterFacturasUseCase;

    @Mock
    private Observer<List<Factura>> facturasObserver;

    private FacturaViewModel viewModel;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);

        // Crear el ViewModel con la aplicación mockeada
        Application application = Mockito.mock(Application.class);
        viewModel = new FacturaViewModel(application);

        // Acceder a los campos privados usando reflexión
        Field getFacturasUseCaseField = FacturaViewModel.class.getDeclaredField("getFacturasUseCase");
        Field filterFacturasUseCaseField = FacturaViewModel.class.getDeclaredField("filterFacturasUseCase");

        // Hacer accesibles los campos privados
        getFacturasUseCaseField.setAccessible(true);
        filterFacturasUseCaseField.setAccessible(true);

        // Asignar los mocks a los campos privados
        getFacturasUseCaseField.set(viewModel, getFacturasUseCase);
        filterFacturasUseCaseField.set(viewModel, filterFacturasUseCase);

        // Enlazar el observador de las facturas
        viewModel.getFacturas().observeForever(facturasObserver);
    }


    @Test
    public void loadFacturas_shouldUpdateFacturasLiveDataOnSuccess() throws NoSuchFieldException, IllegalAccessException {
        // Preparar los datos mockeados
        List<Factura> mockFacturas = Arrays.asList(
                new Factura("Pagada", 100f,"20/02/2025"),
                new Factura("Pendiente de pago", 150f,"19/02/2025")
        );

        // Obtener la referencia al campo 'getFacturasUseCase' en FacturaViewModel
        Field field = FacturaViewModel.class.getDeclaredField("getFacturasUseCase");
        field.setAccessible(true);  // Hacer el campo accesible, aunque sea privado

        // Configurar el campo con el mock
        doAnswer(invocation -> {
            GetFacturasUseCaseCallback callback = invocation.getArgument(1);
            callback.onSuccess(mockFacturas); // Call the callback with mocked data
            return null; // Return null for void methods
        }).when(getFacturasUseCase).execute(Mockito.anyBoolean(), Mockito.any());

        // Asignar el mock a la variable privada
        field.set(viewModel, getFacturasUseCase);

        // Llamar al método que debe actualizar el LiveData
        viewModel.loadFacturas(true);

        // Verificar que el LiveData fue actualizado correctamente
        verify(facturasObserver).onChanged(mockFacturas);
    }

    @Test
    public void aplicarFiltros_shouldUpdateFacturasLiveDataWithFilteredResults() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        List<Factura> facturas = Arrays.asList(
                new Factura("Pagada", 100f, "20/02/2025"),
                new Factura("Pendiente de pago", 150f, "19/02/2025")
        );
        List<String> estadosSeleccionados = Arrays.asList("Pagada");
        String fechaInicio = "01/01/2020";
        String fechaFin = "31/12/2025";
        double importeMin = 50.0;
        double importeMax = 150.0;

        // Mock the filtered result
        List<Factura> facturasFiltradas = Arrays.asList(
                new Factura("Pagada", 100f, "20/02/2025")
        );

        // Mock the filterFacturasUseCase
        doAnswer(invocation -> {
            FilterFacturasUseCaseCallback callback = invocation.getArgument(6); // Get the callback argument
            callback.onSuccess(facturasFiltradas); // Call the callback with mocked data
            return null; // Return null for void methods
        }).when(filterFacturasUseCase).filtrarFacturas(
                Mockito.anyList(), Mockito.anyList(), Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(), Mockito.anyDouble(), Mockito.any()
        );

        // Use Reflection to set the private field 'facturasOriginales'
        Field facturasOriginalesField = FacturaViewModel.class.getDeclaredField("facturasOriginales");
        facturasOriginalesField.setAccessible(true); // Make the private field accessible
        facturasOriginalesField.set(viewModel, facturas); // Set the value of the field

        // Act
        viewModel.aplicarFiltros(estadosSeleccionados, fechaInicio, fechaFin, importeMin, importeMax);

        // Assert
        verify(facturasObserver).onChanged(facturasFiltradas);
    }


    @Test
    public void getMaxImporte_shouldReturnCorrectMaximumAmount() throws NoSuchFieldException, IllegalAccessException {
        // Preparar las facturas
        List<Factura> facturas = Arrays.asList(
                new Factura("Pagada", 100f,"20/02/2025"),
                new Factura("Plan de pago", 125f,"18/02/2025"),
                new Factura("Pendiente de pago", 150f,"19/02/2025")
        );

        // Obtener el campo 'facturasOriginales' usando reflexión
        Field field = FacturaViewModel.class.getDeclaredField("facturasOriginales");
        field.setAccessible(true); // Hacer accesible el campo privado

        // Asignar las facturas mockeadas al campo privado
        field.set(viewModel, facturas);

        // Llamar al método que quieres probar
        float maxImporte = viewModel.getMaxImporte();

        // Verificar que el valor retornado es el esperado
        assertEquals(150f, maxImporte, 0.0f);  // El valor máximo es 150f
    }
}
