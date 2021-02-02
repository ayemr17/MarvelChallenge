package com.empresa.myapplication.marvelapp.model.repository.personajes

import com.empresa.myapplication.marvelapp.model.local.ChartEventModel
import com.empresa.myapplication.marvelapp.model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Deferred

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

interface PersonajeRepositoryInter {

    suspend fun getCharactersForApi() : Resource<List<Result>>
    fun getCharactersAndEventsAsync() : Deferred<ChartEventModel>
}