package com.empresa.myapplication.marvelapp._model.local

import androidx.room.*

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

@Dao
interface FavoritosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorito(states : FavoritesEntity)

    @Query("SELECT * FROM _FAVORITOS")
    suspend fun getAllFavoritos() : List<FavoritesEntity>

    @Delete
    suspend fun deleteFavorito (favorito : FavoritesEntity)
}