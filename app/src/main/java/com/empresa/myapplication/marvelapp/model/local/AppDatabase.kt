package com.empresa.myapplication.marvelapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

@Database(
    entities = [
        FavoritesEntity::class
    ], version = 2
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritosDao(): FavoritosDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "marvelChallenge.db"
            ).build()
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}