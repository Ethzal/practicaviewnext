package com.example.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.usecase.GetDetallesUseCase;
import com.example.domain.model.Detalles;
import java.util.List;

public class DetallesViewModel extends ViewModel {
    private final GetDetallesUseCase useCase;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();

    public DetallesViewModel(GetDetallesUseCase useCase) {
        this.useCase = useCase;
    }

    public MutableLiveData<List<Detalles>> getDetalles() {
        return detallesLiveData;
    }

    public void loadDetalles() {
        // Carga inicial
        useCase.refreshDetalles();
        detallesLiveData.setValue(useCase.getDetalles());
    }
}
