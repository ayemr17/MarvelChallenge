package com.empresa.myapplication.marvelapp.util.sharedPreferences

interface SharedPreferencesRepository {

    fun setStatusLogin(status: String)
    fun getStatusLogin(): String
    fun setUser(user: String)
    fun getUser(): String

}