package com.empresa.myapplication.marvelapp.viewModel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

/**
 * Created by Ayelen Merigo on 24/8/2020.
 */

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    var ERROR_SOCKET_CLOSED =
        "socket closed" //este error tambien ocurre cuando el usuario cancela una solicitud de alguna api

    protected var loading = MutableLiveData<String>()
    protected var error = MutableLiveData<String>()
}