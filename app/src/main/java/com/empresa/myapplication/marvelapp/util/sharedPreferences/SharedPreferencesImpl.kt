package com.empresa.myapplication.marvelapp.util.sharedPreferences

import android.content.SharedPreferences
import com.empresa.myapplication.marvelapp.MarvelApp

class SharedPreferencesImpl : SharedPreferencesRepository {

    private val STATUS_LOGIN = "status_login"
    private val USER_LOGIN = "user_login"

    override fun setStatusLogin(status: String) {
        MarvelApp.getPreferencesInstance().edit().putString(STATUS_LOGIN, status).apply()
    }

    override fun getStatusLogin(): String {
        return MarvelApp.getPreferencesInstance().getString(STATUS_LOGIN, "")?: ""
    }

    override fun setUser(user: String) {
        MarvelApp.getPreferencesInstance().edit().putString(USER_LOGIN, user).apply()
    }

    override fun getUser(): String {
        return MarvelApp.getPreferencesInstance().getString(USER_LOGIN, "")?: ""
    }
}