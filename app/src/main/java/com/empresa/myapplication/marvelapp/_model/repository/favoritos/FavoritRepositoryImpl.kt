package com.empresa.myapplication.marvelapp._model.repository.favoritos

import android.content.Context
import androidx.annotation.WorkerThread
import com.empresa.myapplication.marvelapp._model.local.AppDatabase
import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp.util.ResponseObjetBasic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoritRepositoryImpl(context: Context) :
    CoroutineScope, FavoritRepositoryInter {

    private val mDatabase = AppDatabase.getInstance(context)
    private var mFavoritosDao = mDatabase.favoritosDao()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    override suspend fun saveFavoritos(favorito : FavoritosEntity) =
        withContext(Dispatchers.IO) {
            mFavoritosDao.insertFavorito(favorito)
        }

    @WorkerThread
    override suspend fun getAllFavoritos(responseObjetBasic: ResponseObjetBasic<List<FavoritosEntity>>) =
        withContext(Dispatchers.IO) {
            val listfavoritos: List<FavoritosEntity> = mFavoritosDao.getAllFavoritos() ?: ArrayList()
            /*if (listfavoritos?.isNotEmpty()!!) {
                *//*for (odsEntity in listPueblos) {
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                    odsEntity.nombre = decrypt(odsEntity.nombre).toString()
                }*//*
            }*/
            responseObjetBasic.onSuccess(listfavoritos)
        }
}