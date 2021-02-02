package com.empresa.myapplication.marvelapp.viewModel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empresa.myapplication.marvelapp.model.repository.eventos.EventosRepositoryInter

/**
 * Created by Ayelen Merigo on 25/8/2020.
 */

class EventosVMFactory(private val repoEvento: EventosRepositoryInter) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(EventosRepositoryInter::class.java).newInstance(repoEvento)
    }
}