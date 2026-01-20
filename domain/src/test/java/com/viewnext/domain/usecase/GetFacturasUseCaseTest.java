package com.viewnext.domain.usecase;

import com.viewnext.domain.repository.GetFacturasRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
    public void refreshFacturasTrue_callsRepositoryWithTrue() {
        // Arrange
        // @Before

        // Act
        useCase.refreshFacturas(true);

        // Assert
        verify(mockRepository).refreshFacturas(true);
        verifyNoMoreInteractions(mockRepository);
    }

    @Test
    public void refreshFacturasFalse_callsRepositoryWithFalse() {
        // Arrange
        // @Before

        // Act
        useCase.refreshFacturas(false);

        // Assert
        verify(mockRepository).refreshFacturas(false);
        verifyNoMoreInteractions(mockRepository);
    }
}
