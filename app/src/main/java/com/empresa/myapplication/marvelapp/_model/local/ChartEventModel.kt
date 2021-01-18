package com.empresa.myapplication.marvelapp._model.local

import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource

data class ChartEventModel(
    val charactersList: Resource<List<Result>>?,
    val eventsList: Resource<List<ResultEventos>>?
) {
}