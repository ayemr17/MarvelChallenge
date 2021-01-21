package com.empresa.myapplication.marvelapp._model.repository.favoritos

import com.empresa.myapplication.marvelapp._model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

interface FavoritRepositoryInter {

    suspend fun saveFavorite(favorito : FavoritesEntity)

    suspend fun getAllFavoritos() : Resource<List<FavoritesEntity>>

    suspend fun deleteFavorite(favorito : FavoritesEntity)

    fun getCharactersProduce(): ReceiveChannel<FavoritesEntity>

}