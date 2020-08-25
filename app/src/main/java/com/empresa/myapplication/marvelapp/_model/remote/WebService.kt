package com.empresa.myapplication.marvelapp._model.remote


import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.EventosPojo
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.PersonajesPojo
import com.empresa.myapplication.marvelapp.vo.Resource
import retrofit2.http.GET

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

interface WebService {

    // aca es donde hacemos concretamente la llamada con su respectiva ruta

    @GET("characters?apikey=3a783b25c80e1c44875356dd363f272d&hash=51a3ecf2f92a23817992a2663183325e&ts=1")
    suspend fun getAllCharacters() : PersonajesPojo

    @GET("events?orderBy=startDate&limit=25&apikey=3a783b25c80e1c44875356dd363f272d&hash=51a3ecf2f92a23817992a2663183325e&ts=1")
    suspend fun getAllEvents() : EventosPojo

}