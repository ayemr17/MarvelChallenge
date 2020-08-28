package com.empresa.myapplication.marvelapp._model.repository.personajes

import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajeRepositoryImpl(private  val dataSourceApi: DataSourceApi) : PersonajeRepositoryInter {

    // aca es donde manejamos los datos devueltos por la api y proximamente room tambien

    override suspend fun getCharactersForApi(): Resource<List<Result>> {
        return dataSourceApi.getAllCharacters()
    }

}