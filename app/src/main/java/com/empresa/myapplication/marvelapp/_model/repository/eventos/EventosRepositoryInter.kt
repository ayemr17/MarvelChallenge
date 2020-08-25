package com.empresa.myapplication.marvelapp._model.repository.eventos

import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Events
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

interface EventosRepositoryInter {

    suspend fun getEventsForApi() : Resource<List<ResultEventos>>

}