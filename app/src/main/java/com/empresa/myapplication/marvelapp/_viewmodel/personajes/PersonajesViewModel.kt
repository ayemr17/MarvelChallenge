package com.empresa.myapplication.marvelapp._viewmodel.personajes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajesViewModel(private val repoPersonaje : PersonajeRepositoryInter) : ViewModel() {

    val personajesList = liveData(Dispatchers.IO) {
        emit(Resource.Loafing())
        try {
            emit(repoPersonaje.getCharactersForApi())
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }
}