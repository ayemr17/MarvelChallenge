package com.empresa.myapplication.marvelapp.model.repository.personajes

import com.empresa.myapplication.marvelapp.model.local.ChartEventModel
import com.empresa.myapplication.marvelapp.model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp.model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp.model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.model.repository.eventos.EventosRepositoryInter
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class PersonajeRepositoryImpl(private val dataSourceApi: DataSourceApi) : PersonajeRepositoryInter,
    EventosRepositoryInter, CoroutineScope {

    // creamos un coroutineScope para poder crear corrutinas mediante este y no utilizando el GlobalScope o CoroutineScope propiamente dichos
    // de esta forma podemos llamar directamente a la funciona launch o async sin tener que mencionarla cada vez que querramos iniciar una corrutina
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override suspend fun getEventsForApi(): Resource<List<ResultEventos>> {
        return dataSourceApi.getAllEvents()
    }

    override suspend fun getCharactersForApi(): Resource<List<Result>> {
        return dataSourceApi.getAllCharacters()
    }

    override fun getCharactersAndEventsAsync() = async {
        val charactersAsync = async { getCharactersForApi() }
        val eventsAsync = async { getEventsForApi() }

        return@async ChartEventModel(
            charactersAsync.await(),
            eventsAsync.await()
        )
    }

    //Si no hubiesemos creado la variable armando nuestro scope, tendriamos que haber explicitado en la funcion que contexto se iba a utilizar y que scope
    fun getCharactersAndEventsAsync2() = CoroutineScope(Dispatchers.Main).async {
        val charactersAsync = async(Dispatchers.IO) { getCharactersForApi() }
        val eventsAsync = async(Dispatchers.IO) { getEventsForApi() }

        return@async ChartEventModel(
            charactersAsync.await(),
            eventsAsync.await()
        )
    }

    // de esta forma no se debe hacer ya que el runBlocking lo que hace es presisamente bloquear el hijo donde se lo esta llamando hasta obtener el resultado
    // es decir que anula el proposito de usar corrutinas
    fun getCharactersAndEvents(): ChartEventModel =
        runBlocking {
            getCharactersAndEventsAsync().await()
        }


    /*fun getCharactersAndEventsAsync() =
        CoroutineScope(Dispatchers.Main).launch {
            ......
    }

    fun getCharactersAndEventsAsync() =
        CoroutineScope(Dispatchers.IO).launch {
            .....
        }

    fun getCharactersAndEventsAsync() =
        CoroutineScope(Dispatchers.Default).launch {
            .....
        }

    fun getCharactersAndEventsAsync() =
        CoroutineScope(Dispatchers.Unconfined).launch {
            .....
        }*/
}