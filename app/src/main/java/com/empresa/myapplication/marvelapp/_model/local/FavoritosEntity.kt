package com.empresa.myapplication.marvelapp._model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Item

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

@Entity(tableName = "_FAVORITOS")
data class FavoritosEntity(
    @PrimaryKey
    val idPersonaje : Int,
    @ColumnInfo(name = "nombrePersonaje")
    val nombre : String = "",
    @ColumnInfo(name = "descripcionPersonaje")
    var descripcion : String = "",
    @ColumnInfo(name = "imagenPersonaje")
    val imagen : String = ""

)