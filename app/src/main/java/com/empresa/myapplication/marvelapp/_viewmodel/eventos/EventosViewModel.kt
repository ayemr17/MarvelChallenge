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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosViewModel(private val repoEventos: EventosRepositoryInter) : ViewModel(), CoroutineScope {

    // creamos un coroutineScope para poder crear corrutinas mediante este y no utilizando el GlobalScope o CoroutineScope propiamente dichos
    // de esta forma podemos llamar directamente a la funciona launch o async sin tener que mencionarla cada vez que querramos iniciar una corrutina
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    private val _eventosList: MutableLiveData<Resource<List<ResultEventos>>> = MutableLiveData()
    val eventosList: LiveData<Resource<List<ResultEventos>>> get() = _eventosList

    fun getEvents() {
        launch {
            var listEvents = repoEventos.getCharactersAndEventsAsync().await()
            _eventosList.postValue(listEvents.eventsList)
        }
    }
}