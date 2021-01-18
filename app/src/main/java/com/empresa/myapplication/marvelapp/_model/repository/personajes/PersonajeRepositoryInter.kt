package com.empresa.myapplication.marvelapp._model.repository.personajes

import com.empresa.myapplication.marvelapp._model.local.ChartEventModel
import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

interface PersonajeRepositoryInter {

    suspend fun getCharactersForApi() : Resource<List<Result>>
    fun getCharactersAndEventsAsync(): Deferred<ChartEventModel>
    fun getCharactersAndEvents(): ChartEventModel

}