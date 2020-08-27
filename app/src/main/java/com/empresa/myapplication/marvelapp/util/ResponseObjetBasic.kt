package com.empresa.myapplication.marvelapp.util

interface ResponseObjetBasic<T> {
    fun onSuccess(entity: T)

    fun onError(message: String)
}