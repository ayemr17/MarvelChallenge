package com.empresa.myapplication.marvelapp._viewmodel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryImpl
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryInter
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosVMFactory(private val repoEvento: EventosRepositoryInter) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EventosRepositoryInter::class.java).newInstance(repoEvento)
    }
}