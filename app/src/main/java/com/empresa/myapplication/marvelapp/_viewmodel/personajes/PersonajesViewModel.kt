package com.empresa.myapplication.marvelapp._viewmodel.personajes

import android.app.Activity
import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoritRepositoryInter
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajesViewModel(private val repoPersonaje : PersonajeRepositoryInter) : ViewModel() {

    val STATUS_LOGIN = "status_login"

    val personajesList = liveData(Dispatchers.IO) {
        emit(Resource.Loafing())
        try {
            emit(repoPersonaje.getCharactersForApi())
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }


}