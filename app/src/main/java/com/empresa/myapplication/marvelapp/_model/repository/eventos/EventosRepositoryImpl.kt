package com.empresa.myapplication.marvelapp._model.repository.eventos

import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosRepositoryImpl(private  val dataSourceApi: DataSourceApi) : EventosRepositoryInter {

    // aca es donde manejamos los datos devueltos por la api y proximamente room tambien

    override suspend fun getEventsForApi(): Resource<List<ResultEventos>> {
        return dataSourceApi.getAllEvents()
    }
}