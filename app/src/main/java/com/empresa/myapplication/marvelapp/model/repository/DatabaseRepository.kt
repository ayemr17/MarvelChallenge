package com.empresa.myapplication.marvelapp.model.repository

import android.content.Context
import android.util.Log
import com.empresa.myapplication.marvelapp.model.local.AppDatabase
import com.empresa.myapplication.marvelapp.model.local.FavoritosDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class DatabaseRepository(private var context: Context) : CoroutineScope {

    private val TAG: String = DatabaseRepository::class.java.simpleName

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    var favoritosDao : FavoritosDao

    init {
        AppDatabase.getInstance(context).let {
            favoritosDao = it.favoritosDao()
        }
    }

    fun populateDatabaseLocal() {
        try {
            // SYNCS AQUI
            populateFavoritos()

        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun populateFavoritos() {}
}