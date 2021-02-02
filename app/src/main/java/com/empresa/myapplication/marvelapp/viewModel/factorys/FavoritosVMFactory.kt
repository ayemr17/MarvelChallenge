package com.empresa.myapplication.marvelapp.viewModel.factorys

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.empresa.myapplication.marvelapp.model.repository.favoritos.FavoritRepositoryInter

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoritosVMFactory(private val repoFavoritos: FavoritRepositoryInter) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(FavoritRepositoryInter::class.java).newInstance(repoFavoritos)
    }
}