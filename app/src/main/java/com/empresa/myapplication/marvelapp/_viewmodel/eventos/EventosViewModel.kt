package com.empresa.myapplication.marvelapp._viewmodel.eventos

import androidx.lifecycle.*
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryImpl
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryInter
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryImpl
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosViewModel(private val repoEventos: EventosRepositoryInter) : ViewModel() {

    private val _eventosList: MutableLiveData<Resource<List<ResultEventos>>> = MutableLiveData()
    val eventosList: LiveData<Resource<List<ResultEventos>>> get() = _eventosList

    private var listEvents = repoEventos.getCharactersAndEvents()

    fun getEvents() {
        _eventosList.postValue(listEvents.eventsList)
    }
}