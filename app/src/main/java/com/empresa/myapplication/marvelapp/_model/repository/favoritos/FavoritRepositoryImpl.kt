package com.empresa.myapplication.marvelapp._model.repository.favoritos

import com.empresa.myapplication.marvelapp._model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.sql.DataSource

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoritRepositoryImpl(private val dataSource: DataSourceRoom) : FavoritRepositoryInter {

    override suspend fun saveFavoritos(favorito : FavoritosEntity) {
        dataSource.saveFavoritos(favorito)
    }

    override suspend fun getAllFavoritos() : Resource<List<FavoritosEntity>> {
        return dataSource.getFavoritos()
    }

    override suspend fun deleteFavorito(favorito: FavoritosEntity) {
        dataSource.deleteFavorito(favorito)
    }
}