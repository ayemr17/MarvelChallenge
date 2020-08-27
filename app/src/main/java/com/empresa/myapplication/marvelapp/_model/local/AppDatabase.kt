package com.empresa.myapplication.marvelapp._model.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

@Database(
    entities = [
        FavoritosEntity::class
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