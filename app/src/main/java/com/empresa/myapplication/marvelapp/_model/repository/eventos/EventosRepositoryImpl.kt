package com.empresa.myapplication.marvelapp._model.repository.eventos

import com.empresa.myapplication.marvelapp._model.remote.DataSource
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.vo.Resource

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosRepositoryImpl(private  val dataSource: DataSource) : EventosRepositoryInter {
    override suspend fun getEventsForApi(): Resource<List<ResultEventos>> {
        return dataSource.getAllEvents()
    }
}