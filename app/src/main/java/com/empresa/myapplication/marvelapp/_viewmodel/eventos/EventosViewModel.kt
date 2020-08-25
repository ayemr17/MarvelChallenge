package com.empresa.myapplication.marvelapp._viewmodel.eventos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosViewModel(private val repoEventos : EventosRepositoryInter) : ViewModel() {

    val eventosList = liveData(Dispatchers.IO) {
        emit(Resource.Loafing())
        try {
            emit(repoEventos.getEventsForApi())
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }
}