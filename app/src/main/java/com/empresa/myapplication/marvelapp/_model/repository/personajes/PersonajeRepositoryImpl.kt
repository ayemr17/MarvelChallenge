package com.empresa.myapplication.marvelapp._model.repository.personajes

import com.empresa.myapplication.marvelapp._model.local.PersonajesEntity
import com.empresa.myapplication.marvelapp._model.remote.DataSource
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajeRepositoryImpl(private  val dataSource: DataSource) : PersonajeRepositoryInter {

    override suspend fun getCharactersForApi(): Resource<List<Result>> {
        return dataSource.getAllCharacters()
    }

    override suspend fun getCharactersForRoom(): Resource<List<PersonajesEntity>> {
        TODO("Not yet implemented")
    }
}