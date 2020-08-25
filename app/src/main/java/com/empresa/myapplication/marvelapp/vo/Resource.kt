package com.empresa.myapplication.marvelapp.vo

sealed class Resource<out T> {
    class Loafing<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val exception: Exception) : Resource<T>()
}