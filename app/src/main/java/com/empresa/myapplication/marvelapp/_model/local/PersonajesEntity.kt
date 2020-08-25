package com.empresa.myapplication.marvelapp._model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Comics
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Item
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

// Ya esta creandose la clase para avanzar con guardado en Room

@Entity
@Parcelize
data class PersonajesEntity(
    @PrimaryKey()
    val idPersonaje : Int,
    @ColumnInfo(name = "nombrePersonaje")
    val nombre : String = "",
    @ColumnInfo(name = "descripcionPersonaje")
    var descripcion : String = "",
    @ColumnInfo(name = "imagenPersonaje")
    val imagen : String = ""
) : Parcelable {


}