package com.dozze.di

import com.dozze.storage.database.DozzeDatabase
import com.dozze.storage.database.buildDozzeDatabase
import com.dozze.storage.database.dao.TimerDao
import com.dozze.storage.datastore.Datastore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val storageModule = module {
    singleOf(::Datastore)
    single<DozzeDatabase> { buildDozzeDatabase(builder = get()) }
    single<TimerDao> { get<DozzeDatabase>().getTimerDao() }
}