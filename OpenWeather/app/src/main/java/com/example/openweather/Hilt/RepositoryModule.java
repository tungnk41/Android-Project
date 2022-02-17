package com.example.openweather.Hilt;

import com.example.openweather.Hilt.Qualifier.LocalRepository;
import com.example.openweather.Hilt.Qualifier.RemoteRepository;
import com.example.openweather.Repository.LocalRepository.LocalRepositoryImpl;
import com.example.openweather.Repository.RemoteRepository.RemoteRepositoryImpl;
import com.example.openweather.Repository.Repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {

    @Binds
    @RemoteRepository
    abstract Repository bindRemoteRepository(RemoteRepositoryImpl remoteRepository);

    @Binds
    @LocalRepository
    abstract Repository bindLocalRepository(LocalRepositoryImpl localRepository);

}
