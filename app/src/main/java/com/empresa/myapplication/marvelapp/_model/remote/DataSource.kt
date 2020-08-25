package com.empresa.myapplication.marvelapp._model.remote

import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.vo.Resource
import com.empresa.myapplication.marvelapp.vo.RetrofitClient

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

class DataSource {

    /*private val generatePersonajeList = listOf(
        Personajes("https://www.cocinavital.mx/wp-content/uploads/2018/03/margarita_tradicional-e1582054510330.jpg", "Margarita", "con tequila, limon y azucar"),
        Personajes("https://www.cronista.com/export/sites/diarioelcronista/img/2019/03/18/negroni_2.jpg_1150498286.jpg", "Negroni", "con campari, carpano y gin"),
        Personajes("https://www.mojitos.com.ar/images/fotos/prd/41_Caipiroshka.jpg", "caipiroshka", "con vodka, limon y azucar"),
        Personajes("https://www.liquor.com/thmb/F4UibVQr8U9E7Y2AkCag0njUC-E=/720x540/smart/filters:no_upscale()/__opt__aboutcom__coeus__resources__content_migration__liquor__2017__12__20073201__white-russian-720x720-article-cbe4b9a832c64f8da0bb09407caefa7f.jpg", "White Russian", "con vodka, licor de cafe y crema")
    )

    fun getPersonajesList() : Resource<List<Personajes>> {
        return Resource.Success(generatePersonajeList)
    }*/

    suspend fun getAllCharacters() : Resource<List<Result>> {
        return Resource.Success(RetrofitClient.webservice.getAllCharacters().data?.results!!)
    }

    suspend fun getAllEvents() : Resource<List<ResultEventos>> {
        return Resource.Success(RetrofitClient.webservice.getAllEvents().data?.resultEventos!!)
    }

}