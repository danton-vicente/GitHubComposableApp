package com.example.composablegithubapp.domain.di

import com.example.composablegithubapp.domain.repositories.IMainRepository
import com.example.composablegithubapp.domain.repositories.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMainRepository(mainRepository: MainRepository): IMainRepository
}