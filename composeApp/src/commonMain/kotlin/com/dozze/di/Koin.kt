package com.dozze.di

import com.dozze.requirements.LibrariesDependencyFactory
import com.dozze.requirements.librariesModule
import com.dozze.requirements.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(libraryFactory: LibrariesDependencyFactory, appDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        appDeclaration?.invoke(this)
        modules(
            viewModelsModule,
            repositoryModule,
            storageModule,
            platformModule,
            analyticsModule,
            librariesModule(libraryFactory)
        )
    }
}