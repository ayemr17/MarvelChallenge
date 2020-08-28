package com.empresa.myapplication.marvelapp._model.local

import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class DataSourceRoom(private val appDatabase: AppDatabase) {

    suspend fun saveFavoritos(favorito : FavoritosEntity) =
        withContext(Dispatchers.IO) {
            appDatabase.favoritosDao().insertFavorito(favorito)
        }

    suspend fun getFavoritos() : Resource<List<FavoritosEntity>> {
        return Resource.Success(appDatabase.favoritosDao().getAllFavoritos())
    }

    suspend fun deleteFavorito(favorito: FavoritosEntity) =
        withContext(Dispatchers.IO) {
            appDatabase.favoritosDao().deleteFavorito(favorito)
        }
}