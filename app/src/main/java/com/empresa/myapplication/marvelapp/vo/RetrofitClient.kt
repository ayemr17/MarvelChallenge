package com.empresa.myapplication.marvelapp.vo

import com.empresa.myapplication.marvelapp.model.remote.WebService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // creamos una instancia de retrofit base con el convertidor que sustituye lo que antes haciamos con los parceos de los json

    val webservice by lazy {
        Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com:443/v1/public/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }

}