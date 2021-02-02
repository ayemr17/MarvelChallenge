package com.empresa.myapplication.marvelapp.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

@Entity(tableName = "_FAVORITOS")
data class FavoritesEntity(
    @PrimaryKey
    val idPersonaje : Int,
    @ColumnInfo(name = "nombrePersonaje")
    val nombre : String = "",
    @ColumnInfo(name = "descripcionPersonaje")
    var descripcion : String = "",
    @ColumnInfo(name = "imagenPersonaje")
    val imagen : String = ""

)