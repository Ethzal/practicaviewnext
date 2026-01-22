package com.viewnext.data.di;

import com.viewnext.data.repository.GetDetallesRepositoryImpl;
import com.viewnext.domain.repository.GetDetallesRepository;
import com.viewnext.domain.repository.GetFacturasRepository;
import com.viewnext.data.repository.GetFacturasRepositoryImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

import javax.inject.Singleton;

/**
 * Módulo de Dagger para la inyección de dependencias de los repositorios.
 * Este módulo se encarga de vincular las implementaciones concretas de los repositorios con sus interfaces
 * para que Dagger pueda inyectarlas en los lugares necesarios.
 */
@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    /**
     * Vincula la implementación {@link GetFacturasRepositoryImpl} con la interfaz {@link GetFacturasRepository}.
     * Esto permite que Dagger proporcione la implementación concreta siempre que se requiera un {@link GetFacturasRepository}.
     * @param impl Implementación de {@link GetFacturasRepository} que se inyectará.
     * @return Una instancia de {@link GetFacturasRepository} proporcionada por Dagger.
     */
    @Binds
    @Singleton
    public abstract GetFacturasRepository bindGetFacturasRepository(GetFacturasRepositoryImpl impl);

    /**
     * Vincula la implementación {@link GetDetallesRepositoryImpl} con la interfaz {@link GetDetallesRepository}.
     * Esto permite que Dagger proporcione la implementación concreta siempre que se requiera un {@link GetDetallesRepository}.
     * @param impl Implementación de {@link GetDetallesRepository} que se inyectará.
     * @return Una instancia de {@link GetDetallesRepository} proporcionada por Dagger.
     */
    @Binds
    @Singleton
    public abstract GetDetallesRepository bindGetDetallesRepository(GetDetallesRepositoryImpl impl);
}
