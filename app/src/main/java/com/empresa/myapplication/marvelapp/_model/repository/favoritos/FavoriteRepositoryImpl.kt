package com.empresa.myapplication.marvelapp._model.repository.favoritos

import com.empresa.myapplication.marvelapp._model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp._model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class FavoriteRepositoryImpl(private val dataSource: DataSourceRoom) : FavoritRepositoryInter, CoroutineScope {

    // creamos un coroutineScope para poder crear corrutinas mediante este y no utilizando el GlobalScope o CoroutineScope propiamente dichos
    // de esta forma podemos llamar directamente a la funciona launch o async sin tener que mencionarla cada vez que querramos iniciar una corrutina
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override suspend fun saveFavorite(favorite : FavoritesEntity) {
        dataSource.saveFavorite(favorite)
    }

    override suspend fun getAllFavoritos() : Resource<List<FavoritesEntity>> {
        return dataSource.getFavorites()
    }

    override suspend fun deleteFavorite(favorite: FavoritesEntity) {
        dataSource.deleteFavorite(favorite)
    }

    // ejemplo de algo que se podria hacer con el builder Produce de coroutines
    var favoritesCharacters = listOf(
        FavoritesEntity(1, "Batman", "Murcielago", ""),
        FavoritesEntity(2, "Chico Percebe", "Bob Esponja", ""),
        FavoritesEntity(3, "Iron Man", "Team Ironman", ""),
        FavoritesEntity(4, "Groot", "Arbolito", "")
    )

    override fun getCharactersProduce(): ReceiveChannel<FavoritesEntity> = produce {
        favoritesCharacters.forEach { send(it) }
    }
}