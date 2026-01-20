package com.viewnext.domain.usecase;

import com.viewnext.domain.repository.GetDetallesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetDetallesUseCaseTest {

    @Mock
    private GetDetallesRepository mockRepository;

    private GetDetallesUseCase useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetDetallesUseCase(mockRepository);
    }

    @Test
    public void refreshDetalles_callsRepositoryRefresh() {
        // Arrange
        // @Before

        // Act
        useCase.refreshDetalles();

        // Assert
        verify(mockRepository).refreshDetalles();
        verifyNoMoreInteractions(mockRepository);
    }
}
