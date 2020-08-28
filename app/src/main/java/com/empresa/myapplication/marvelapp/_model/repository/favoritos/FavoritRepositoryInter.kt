package com.empresa.myapplication.marvelapp._model.repository.favoritos

import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

interface FavoritRepositoryInter {

    suspend fun saveFavoritos(favorito : FavoritosEntity)

    suspend fun getAllFavoritos() : Resource<List<FavoritosEntity>>

    suspend fun deleteFavorito(favorito : FavoritosEntity)

}