package com.empresa.myapplication.marvelapp._viewmodel.personajes

import androidx.lifecycle.*
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.*

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajesViewModel(private val repoPersonaje: PersonajeRepositoryInter) : ViewModel() {

    private val _charactersList : MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    val charactersList: LiveData<Resource<List<Result>>> get() = _charactersList

    fun getCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            var listCharacters = repoPersonaje.getCharactersAndEventsAsync().await()
            _charactersList.postValue(listCharacters.charactersList)
        }
    }
}