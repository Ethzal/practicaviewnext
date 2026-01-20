package com.viewnext.presentation.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.inject.Provider;
import com.viewnext.domain.repository.GetFacturasRepository;
import com.viewnext.domain.usecase.FilterFacturasUseCase;
import com.viewnext.domain.usecase.GetFacturasUseCase;
import com.viewnext.presentation.viewmodel.FacturaViewModel;
import javax.inject.Inject;

public class FacturaViewModelFactory implements ViewModelProvider.Factory {

    private final Provider<GetFacturasRepository> repositoryProvider;
    private final Provider<FilterFacturasUseCase> filterUseCaseProvider;

    @Inject
    public FacturaViewModelFactory(
            Provider<GetFacturasRepository> repositoryProvider,
            Provider<FilterFacturasUseCase> filterUseCaseProvider) {
        this.repositoryProvider = repositoryProvider;
        this.filterUseCaseProvider = filterUseCaseProvider;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FacturaViewModel.class)) {
            GetFacturasUseCase getFacturasUseCase = new GetFacturasUseCase(repositoryProvider.get());
            FilterFacturasUseCase filterFacturasUseCase = filterUseCaseProvider.get();
            return (T) new FacturaViewModel(getFacturasUseCase, filterFacturasUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
