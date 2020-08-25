package com.empresa.myapplication.marvelapp._model.repository.personajes

import com.empresa.myapplication.marvelapp._model.local.PersonajesEntity
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

interface PersonajeRepositoryInter {
    // intefaz que funciona para abstraer las consultas

    suspend fun getCharactersForApi() : Resource<List<Result>>


    suspend fun getCharactersForRoom() : Resource<List<PersonajesEntity>>
}