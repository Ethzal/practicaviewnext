package com.viewnext.domain.usecase;

import com.viewnext.domain.model.Detalles;
import com.viewnext.domain.repository.DetallesCallback;
import com.viewnext.domain.repository.GetDetallesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.List;

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
        useCase.refreshDetalles(new DetallesCallback<>() {
            @Override
            public void onSuccess(List<Detalles> detalles) {
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });

        // Assert
        verify(mockRepository).refreshDetalles(any(DetallesCallback.class));
        verifyNoMoreInteractions(mockRepository);
    }
}