package com.empresa.myapplication.marvelapp._viewmodel

import androidx.lifecycle.*
import com.empresa.myapplication.marvelapp._model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoritRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoritesViewModel(private val favoriteRepository : FavoritRepositoryInter) : ViewModel()  {

    private val _favoritesList : MutableLiveData<List<FavoritesEntity>> = MutableLiveData()
    val favoritesList: LiveData<List<FavoritesEntity>> get() = _favoritesList

    val favoritosList = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(favoriteRepository.getAllFavoritos())
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun insertFavorite(favorite: FavoritesEntity) {
        viewModelScope.launch {
            favoriteRepository.saveFavorite(favorite)
        }
    }

    fun deleteFavorite(favorite: FavoritesEntity) {
        viewModelScope.launch {
            favoriteRepository.deleteFavorite(favorite)
        }
    }

    fun getFavoritesList() {
        CoroutineScope(Dispatchers.IO).launch {
            val channel = favoriteRepository.getCharactersProduce()
            var list = ArrayList<FavoritesEntity>()
            channel.consumeEach { favoriteEntity ->
                list.add(favoriteEntity)
            }
            _favoritesList.postValue(list)
        }
    }
}