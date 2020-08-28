package com.empresa.myapplication.marvelapp._viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoritRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoritosViewModel(private val repoFavoritos : FavoritRepositoryInter) : ViewModel()  {

    val favoritosList = liveData(Dispatchers.IO) {
        emit(Resource.Loafing())
        try {
            emit(repoFavoritos.getAllFavoritos())
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun insertFavorito(favorito: FavoritosEntity) {
        viewModelScope.launch {
            repoFavoritos.saveFavoritos(favorito)
        }
    }

    fun deleteFavorito(favorito: FavoritosEntity) {
        viewModelScope.launch {
            repoFavoritos.deleteFavorito(favorito)
        }
    }
}