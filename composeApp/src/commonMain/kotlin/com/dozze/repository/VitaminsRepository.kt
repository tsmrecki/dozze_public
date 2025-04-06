package com.dozze.repository

import com.dozze.storage.datastore.Datastore

internal interface VitaminsRepository {
    fun welcome(): String
}

internal class VitaminsRepositoryImpl(datastore: Datastore) : VitaminsRepository {
    override fun welcome(): String {
        return "THIS IS COMPOSE MULTIPLATFORM."
    }
}
