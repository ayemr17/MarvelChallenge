package com.empresa.myapplication.marvelapp._model.local

import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class DataSourceRoom(private val appDatabase: AppDatabase) {

    suspend fun saveFavorite(favorito : FavoritesEntity) =
        withContext(Dispatchers.IO) {
            appDatabase.favoritosDao().insertFavorito(favorito)
        }

    suspend fun getFavorites() : Resource<List<FavoritesEntity>> {
        return Resource.Success(appDatabase.favoritosDao().getAllFavoritos())
    }

    suspend fun deleteFavorite(favorito: FavoritesEntity) =
        withContext(Dispatchers.IO) {
            appDatabase.favoritosDao().deleteFavorito(favorito)
        }
}