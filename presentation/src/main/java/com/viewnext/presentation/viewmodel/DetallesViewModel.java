package com.viewnext.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//import com.viewnext.data.repository.GetDetallesRepositoryImpl;
import com.viewnext.domain.repository.DetallesCallback;
import com.viewnext.domain.repository.GetDetallesRepository;
import com.viewnext.domain.usecase.GetDetallesUseCase;
import com.viewnext.domain.model.Detalles;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DetallesViewModel extends ViewModel {
    private final GetDetallesUseCase useCase;
    private final MutableLiveData<List<Detalles>> detallesLiveData = new MutableLiveData<>();
    private final GetDetallesRepository repository;

    @Inject
    public DetallesViewModel(GetDetallesUseCase useCase, GetDetallesRepository repository) {
        this.useCase = useCase;
        this.repository = repository;
    }

    public MutableLiveData<List<Detalles>> getDetalles() {
        return detallesLiveData;
    }

    public void loadDetalles() {
        useCase.refreshDetalles(new DetallesCallback<>() {
            @Override
            public void onSuccess(List<Detalles> result) {
                detallesLiveData.setValue(result);  // Actualiza los detalles
            }

            @Override
            public void onFailure(Throwable error) {
                // Error
            }
        });
    }
}