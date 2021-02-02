package com.empresa.myapplication.marvelapp.model.repository.eventos

import com.empresa.myapplication.marvelapp.model.local.ChartEventModel
import com.empresa.myapplication.marvelapp.model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Deferred

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

interface EventosRepositoryInter {

    suspend fun getEventsForApi() : Resource<List<ResultEventos>>
    fun getCharactersAndEventsAsync() : Deferred<ChartEventModel>

}