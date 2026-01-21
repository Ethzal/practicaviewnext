package com.viewnext.data.di;

import com.viewnext.domain.repository.GetFacturasRepository;
import com.viewnext.data.repository.GetFacturasRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @Singleton
    public abstract GetFacturasRepository bindGetFacturasRepository(GetFacturasRepositoryImpl impl);
}
