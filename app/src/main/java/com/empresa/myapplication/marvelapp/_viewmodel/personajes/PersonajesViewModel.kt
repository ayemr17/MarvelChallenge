package com.empresa.myapplication.marvelapp._viewmodel.personajes

import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import com.empresa.myapplication.marvelapp._model.local.ChartEventModel
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryImpl
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryInter
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaPersonajesAdapter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_personajes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajesViewModel(private val repoPersonaje: PersonajeRepositoryInter) : ViewModel() {

    private val _charactersList : MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    val charactersList: LiveData<Resource<List<Result>>> get() = _charactersList

    private var listCharacters = repoPersonaje.getCharactersAndEvents()

    fun getCharacters() {
            _charactersList.postValue(listCharacters.charactersList)
    }
}