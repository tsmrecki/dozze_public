package com.dozze.di

import com.dozze.repository.VitaminsRepository
import com.dozze.repository.VitaminsRepositoryImpl
import org.koin.dsl.module

internal val repositoryModule = module {
    single<VitaminsRepository> { VitaminsRepositoryImpl(get()) }
}
