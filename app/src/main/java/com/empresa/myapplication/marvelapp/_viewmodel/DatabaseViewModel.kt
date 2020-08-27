package com.empresa.myapplication.marvelapp._viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.empresa.myapplication.marvelapp._model.repository.DatabaseRepository

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class DatabaseViewModel(app: Application) : AndroidViewModel(app) {
    var mDatabaseRepository : DatabaseRepository? = null

    init {
        mDatabaseRepository = DatabaseRepository(app)
    }
}