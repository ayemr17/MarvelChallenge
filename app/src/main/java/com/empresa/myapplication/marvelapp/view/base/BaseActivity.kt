package com.empresa.myapplication.marvelapp.view.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Ayelen Merigo on 23/8/2020.
 */

abstract class BaseActivity : AppCompatActivity() {

    protected open val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "OPEN ACTIVITY $TAG")
        super.onCreate(savedInstanceState)
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}