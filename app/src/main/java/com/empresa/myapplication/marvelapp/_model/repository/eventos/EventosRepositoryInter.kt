package com.empresa.myapplication.marvelapp._model.repository.eventos

import com.empresa.myapplication.marvelapp._model.local.ChartEventModel
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Events
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Deferred

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

interface EventosRepositoryInter {

    suspend fun getEventsForApi() : Resource<List<ResultEventos>>
    suspend fun getCharactersForApi(): Resource<List<Result>>
    fun getCharactersAndEventsAsync(): Deferred<ChartEventModel>
    fun getCharactersAndEvents(): ChartEventModel

}