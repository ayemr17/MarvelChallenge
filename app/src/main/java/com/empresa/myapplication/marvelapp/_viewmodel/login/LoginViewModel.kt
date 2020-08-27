package com.empresa.myapplication.marvelapp._viewmodel.login

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel

/**
 * Created by Ayelen Merigo on 27/8/2020.
 */

class LoginViewModel : ViewModel() {

    fun showAlert(context: Context, mensaje: String) {
        var mensajeEmit = ""

        if (mensaje.contains("Passwird should be at least 6 characters")) {
            mensajeEmit = "Su contraseña debe tener más de 6 caracteres."
        }
        if (mensaje.contains("The email address is badly formatted")) {
            mensajeEmit = "El email debe tener formato email: \\n\" +\n" +
                    "                        \" 'usuario@mail.com'"
        }
        if (mensaje.contains("The password is invalid or the user does not have a password")) {
            mensajeEmit = "La contraseña es incorrecta."
        }
        if (mensaje.contains("There is no user record corresponding to this identifier")) {
            mensajeEmit = "El email no está registrado como usuario de la aplicación."
        }
        if (mensaje.contains("The email address is already in use by another account")) {
            mensajeEmit = "Esta cuenta de correo ya está registrada como usuario de la aplicación."
        }
        if (mensaje.contains("An account already exists whit the same email address but different sign-in credentials")) {
            mensajeEmit = "La cuenta ya esta registrada pero con otras credenciales."
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage(mensajeEmit)
        builder.setPositiveButton("Aceptar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

}