package com.empresa.myapplication.marvelapp._model.local

import androidx.room.*

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

@Dao
interface FavoritosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorito(states : FavoritosEntity)

    @Query("SELECT * FROM _FAVORITOS")
    fun getAllFavoritos() : List<FavoritosEntity>?
}