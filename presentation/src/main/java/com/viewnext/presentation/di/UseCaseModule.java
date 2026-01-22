package com.viewnext.presentation.di;

import com.viewnext.domain.repository.GetDetallesRepository;
import com.viewnext.domain.repository.GetFacturasRepository;
import com.viewnext.domain.usecase.FilterFacturasUseCase;
import com.viewnext.domain.usecase.GetDetallesUseCase;
import com.viewnext.domain.usecase.GetFacturasUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;

@Module
@InstallIn(ViewModelComponent.class)
public class UseCaseModule {

    @Provides
    public static GetFacturasUseCase provideGetFacturasUseCase(
            GetFacturasRepository repository
    ) {
        return new GetFacturasUseCase(repository);
    }

    @Provides
    public static FilterFacturasUseCase provideFilterFacturasUseCase() {
        return new FilterFacturasUseCase();
    }

    @Provides
    public static GetDetallesUseCase provideGetDetallesUseCase(
            GetDetallesRepository repository
    ) {
        return new GetDetallesUseCase(repository);
    }
}
