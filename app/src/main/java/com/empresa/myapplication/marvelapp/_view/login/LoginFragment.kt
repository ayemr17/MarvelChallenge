package com.empresa.myapplication.marvelapp._view.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import java.util.ArrayList

class LoginFragment : Fragment() {

    private val RC_SIGN_IN = 123
    lateinit var providers: ArrayList<AuthUI.IdpConfig>
    private lateinit var auth: FirebaseAuth
    val STATUS_LOGIN = "status_login"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        generarLoginUI()
    }

    fun generarLoginUI() {
        // elegimos las opciones de logueo para nuestra app
        providers = arrayListOf(
            AuthUI.IdpConfig.FacebookBuilder().build(),  // logueo con facebook
            AuthUI.IdpConfig.EmailBuilder().build()  // logueo con email y pass
        )
        showSignInOptions(providers)
    }

    private fun showSignInOptions(providers: ArrayList<AuthUI.IdpConfig>) {
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.marvel_png_login)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // Logueo exitoso
                // Guardamos estado de logueado en el SharedPreference y nos vamos a la pantalla principal
                saveStatusLogin(getString(R.string.logueado))
                findNavController().navigate(R.id.action_loginFragment_to_viewPagerFragment)
            } else {
                // algo fallo en el logueo
                Toast.makeText(
                    requireContext(),
                    response?.error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                (activity as AppCompatActivity?)!!.finish()
            }
        }
    }

    private fun saveStatusLogin(status: String) {
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferencias), Context.MODE_PRIVATE
        )
            ?: return
        with(sharedPref.edit()) {
            putString(STATUS_LOGIN, status)
            commit()
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
            .signOut(requireActivity().applicationContext)
            .addOnCompleteListener {
                // se cambia el status de login guardado en las preferencias
                saveStatusLogin(getString(R.string.deslogueado))
                showSignInOptions(providers)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.fallo_deslogueo),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}