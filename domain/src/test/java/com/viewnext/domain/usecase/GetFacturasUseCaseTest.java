package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Factura;
import com.viewnext.domain.repository.GetFacturasRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

public class GetFacturasUseCaseTest {

    @Mock
    private GetFacturasRepository mockRepository;

    private GetFacturasUseCase useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetFacturasUseCase(mockRepository);
    }

    @Test
    public void execute_true_callsRepositoryWithTrue() {
        // Arrange
        GetFacturasUseCase.Callback callback = mock(GetFacturasUseCase.Callback.class);

        ArgumentCaptor<GetFacturasRepository.RepositoryCallback> captor =
                ArgumentCaptor.forClass(GetFacturasRepository.RepositoryCallback.class);

        // Act
        useCase.execute(true, callback);

        // Assert
        verify(mockRepository).refreshFacturas(eq(true), captor.capture());
        verifyNoMoreInteractions(mockRepository);

        // Simular que el repositorio devuelve éxito
        List<Factura> facturasFake = Collections.emptyList();
        captor.getValue().onSuccess(facturasFake);

        verify(callback).onSuccess(facturasFake);
    }

    @Test
    public void execute_false_callsRepositoryWithFalse() {
        // Arrange
        GetFacturasUseCase.Callback callback = mock(GetFacturasUseCase.Callback.class);
        ArgumentCaptor<GetFacturasRepository.RepositoryCallback> captor =
                ArgumentCaptor.forClass(GetFacturasRepository.RepositoryCallback.class);

        // Act
        useCase.execute(false, callback);

        // Assert
        verify(mockRepository).refreshFacturas(eq(false), captor.capture());
        verifyNoMoreInteractions(mockRepository);

        // Error
        String errorMsg = "Error fake";
        captor.getValue().onError(errorMsg);

        verify(callback).onError(errorMsg);
    }
}
