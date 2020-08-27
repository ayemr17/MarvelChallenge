package com.empresa.myapplication.marvelapp._view.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp.util.ProviderType
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login_new.*
import kotlinx.android.synthetic.main.fragment_login_new.view.*

class LoginFragmentNew : Fragment(), BasicMethods {

    val STATUS_LOGIN = "status_login"
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {

    }

    override fun init() {

    }

    override fun initListeners() {
        registrarse_button_fragmentLogin.setOnClickListener {
            // ESCONDER EL TECLADO
            hideKeyboard(requireContext())

            if (validateData()) {
                ingresarConEmail(
                    0,
                    usuario_textImputLayout_activityLogin.textInputEditText3.text.toString(),
                    contraseña_textImputLayout_activityLogin.textInputEditText4.text.toString()
                )
            }
        }

        login_button_fragmentLogin.setOnClickListener {
            // ESCONDER EL TECLADO
            hideKeyboard(requireContext())

            if (validateData()) {
                ingresarConEmail(
                    1,
                    usuario_textImputLayout_activityLogin.textInputEditText3.text.toString(),
                    contraseña_textImputLayout_activityLogin.textInputEditText4.text.toString()
                )
            }
        }

        loginFacebook_button_loginFragment.setOnClickListener {
            // ESCONDER EL TECLADO
            hideKeyboard(requireContext())

            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

            ingresarConEmail(
                2,
                usuario_textImputLayout_activityLogin.textInputEditText3.text.toString(),
                contraseña_textImputLayout_activityLogin.textInputEditText4.text.toString()
            )
        }
    }

    fun validateData(): Boolean {
        if (usuario_textImputLayout_activityLogin.textInputEditText3.text!!.isNotEmpty() && contraseña_textImputLayout_activityLogin.textInputEditText4.text!!.isNotEmpty()) {
            return true
        }
        return false
    }

    fun ingresarConEmail(operacion: Int, email: String, pass: String) {
        when (operacion) {
            0 -> {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        email,
                        pass
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            goHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            progressBar.visibility = View.GONE
                            usuario_textImputLayout_activityLogin.textInputEditText3.text = null
                            contraseña_textImputLayout_activityLogin.textInputEditText4.text = null
                            showAlert(it.exception?.message.toString())
                        }
                    }
            }
            1 -> {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        email,
                        pass
                    )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            goHome(it.result?.user?.email ?: "", ProviderType.BASIC)
                        } else {
                            progressBar.visibility = View.GONE
                            usuario_textImputLayout_activityLogin.textInputEditText3.text = null
                            contraseña_textImputLayout_activityLogin.textInputEditText4.text = null
                            showAlert(it.exception?.message.toString())
                        }
                    }
            }
            2 -> {
                LoginManager.getInstance().registerCallback(
                    callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult?) {
                            result?.let {
                                val token = it.accessToken

                                val credential = FacebookAuthProvider.getCredential(token.token)

                                FirebaseAuth.getInstance().signInWithCredential(credential)
                                    .addOnCompleteListener { it ->
                                        if (it.isSuccessful) {
                                            goHome(
                                                it.result?.user?.email ?: "",
                                                ProviderType.FACEBOOK
                                            )
                                        } else {
                                            progressBar.visibility = View.GONE
                                            showAlert(it.exception?.message.toString())
                                        }
                                    }
                            }
                        }

                        override fun onCancel() {
                            progressBar.visibility = View.GONE
                            showAlert("Has cancelado la solicitud")
                        }

                        override fun onError(error: FacebookException?) {
                            progressBar.visibility = View.GONE
                            showAlert(error?.message.toString())
                        }

                    })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)

        val dialog = builder.create()
        dialog.show()
    }

    fun goHome(email: String, provider: ProviderType) {
        progressBar.visibility = View.GONE

        hideKeyboard(requireContext())
        saveStatusLogin(getString(R.string.logueado))
        val bundle = Bundle()
        bundle.putString("email", email)
        bundle.putString("provider", provider.name)
        findNavController().navigate(R.id.action_loginFragmentNew_to_viewPagerFragment, bundle)
    }

    private fun saveStatusLogin(status: String) {
        progressBar.visibility = View.VISIBLE

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferencias), Context.MODE_PRIVATE
        )
            ?: return
        with(sharedPref.edit()) {
            putString(STATUS_LOGIN, status)
            commit()
        }
    }

    fun hideKeyboard(ctx: Context) {
        val inputManager = ctx
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val v = (ctx as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}