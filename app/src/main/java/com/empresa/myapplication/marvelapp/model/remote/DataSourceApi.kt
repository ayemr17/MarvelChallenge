package com.empresa.myapplication.marvelapp.model.remote

import com.empresa.myapplication.marvelapp.model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource
import com.empresa.myapplication.marvelapp.vo.RetrofitClient

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class DataSourceApi {
    // Aca iniciamos todas las llamadas a las APIs para que traigan los datos

    suspend fun getAllCharacters() : Resource<List<Result>> {
        return Resource.Success(RetrofitClient.webservice.getAllCharacters().data?.results!!)
    }

    suspend fun getAllEvents() : Resource<List<ResultEventos>> {
        return Resource.Success(RetrofitClient.webservice.getAllEvents().data?.resultEventos!!)
    }

}