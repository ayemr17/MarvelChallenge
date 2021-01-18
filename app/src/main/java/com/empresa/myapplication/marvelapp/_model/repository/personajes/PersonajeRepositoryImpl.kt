package com.empresa.myapplication.marvelapp._model.repository.personajes

import com.empresa.myapplication.marvelapp._model.local.ChartEventModel
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.*

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajeRepositoryImpl(private val dataSourceApi: DataSourceApi) : PersonajeRepositoryInter,
    EventosRepositoryInter {

    override suspend fun getEventsForApi(): Resource<List<ResultEventos>> {
        return dataSourceApi.getAllEvents()
    }

    override suspend fun getCharactersForApi(): Resource<List<Result>> {
        return dataSourceApi.getAllCharacters()
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