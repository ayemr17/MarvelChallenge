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

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    ).also {
                        INSTANCE = it
                    }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "marvelChallenge.db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d("ONCREATE", "Database has been created.")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d("ONOPEN", "Database has been opened.")
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
    }
}