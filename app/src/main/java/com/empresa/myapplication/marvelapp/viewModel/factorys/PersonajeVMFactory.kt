package com.empresa.myapplication.marvelapp.viewModel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empresa.myapplication.marvelapp.model.repository.personajes.PersonajeRepositoryInter

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajeVMFactory(private val repoPersonaje: PersonajeRepositoryInter) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PersonajeRepositoryInter::class.java).newInstance(repoPersonaje)
    }

}