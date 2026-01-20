package com.viewnext.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.viewnext.data.repository.GetDetallesRepositoryImpl;
import com.viewnext.domain.repository.GetDetallesRepository;
import com.viewnext.domain.usecase.GetDetallesUseCase;
import com.viewnext.domain.model.Detalles;
import java.util.List;

public class DetallesViewModel extends ViewModel {
    private final GetDetallesUseCase useCase;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();
    private final GetDetallesRepository repository;

    public DetallesViewModel(GetDetallesUseCase useCase, GetDetallesRepository repository) {
        this.useCase = useCase;
        this.repository = repository;
    }

    public MutableLiveData<List<Detalles>> getDetalles() {
        return detallesLiveData;
    }

    public void loadDetalles() {
        useCase.refreshDetalles();
        ((GetDetallesRepositoryImpl) repository).getDetallesLiveData().observeForever(detallesLiveData::postValue);
    }
}
