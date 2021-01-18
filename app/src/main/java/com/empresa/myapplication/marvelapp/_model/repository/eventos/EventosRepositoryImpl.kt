package com.empresa.myapplication.marvelapp._model.repository.eventos

import com.empresa.myapplication.marvelapp._model.local.ChartEventModel
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.*

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosRepositoryImpl(private val dataSourceApi: DataSourceApi) : EventosRepositoryInter, PersonajeRepositoryInter {

    override suspend fun getCharactersForApi(): Resource<List<Result>> {
        return dataSourceApi.getAllCharacters()
    }

    override suspend fun getEventsForApi(): Resource<List<ResultEventos>> {
        return dataSourceApi.getAllEvents()
    }

    override fun getCharactersAndEventsAsync() = CoroutineScope(Dispatchers.IO).async {
        val charactersAsync = async(Dispatchers.IO) { getCharactersForApi() }
        val eventsAsync = async(Dispatchers.IO) { getEventsForApi() }

        return@async ChartEventModel(
            charactersAsync.await(),
            eventsAsync.await()
        )
    }

    override fun getCharactersAndEvents(): ChartEventModel =
        runBlocking {
            getCharactersAndEventsAsync().await()
        }
}