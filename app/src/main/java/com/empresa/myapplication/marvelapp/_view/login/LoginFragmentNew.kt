package com.empresa.myapplication.marvelapp._view.login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.login.LoginViewModel
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

    private lateinit var loginViewModel: LoginViewModel

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
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun init() {}

    override fun initListeners() {
        usuario_textImputLayout_activityLogin.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    usuario_textImputLayout_activityLogin.isErrorEnabled = false
                }
            }
        })

        contraseña_textImputLayout_activityLogin.editText?.addTextChangedListener(object :
            TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    contraseña_textImputLayout_activityLogin.isErrorEnabled = false
                }
            }
        })

        registrarse_button_fragmentLogin.setOnClickListener {
            // ESCONDER EL TECLADO
            loginViewModel.hideKeyboard(requireContext())

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
            loginViewModel.hideKeyboard(requireContext())

            if (validateData()) {
                ingresarConEmail(
                    1,
                    usuario_textImputLayout_activityLogin.textInputEditText3.text.toString(),
                    contraseña_textImputLayout_activityLogin.textInputEditText4.text.toString()
                )
            }
        }

        loginFacebook_button_loginFragment.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            // ESCONDER EL TECLADO
            loginViewModel.hideKeyboard(requireContext())

            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))

            ingresarConEmail(
                2,
                usuario_textImputLayout_activityLogin.textInputEditText3.text.toString(),
                contraseña_textImputLayout_activityLogin.textInputEditText4.text.toString()
            )
        }
    }

    fun validateData(): Boolean {
        progressBar.visibility = View.VISIBLE
        if (usuario_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
                .isEmpty()
        ) {
            usuario_textImputLayout_activityLogin.error = "Este campo no debe estar vacío."
            progressBar.visibility = View.GONE
            return false
        }
        if (contraseña_textImputLayout_activityLogin.editText?.text
                .toString()
                .toLowerCase()
                .trim()
                .isEmpty()
        ) {
            contraseña_textImputLayout_activityLogin.error = "Este campo no debe estar vacío."
            progressBar.visibility = View.GONE
            return false
        }
        contraseña_textImputLayout_activityLogin.error = null
        return true
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
                            loginViewModel.showAlert(requireContext(), it.exception?.message.toString())
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

                            loginViewModel.showAlert(requireContext(), it.exception?.message.toString())
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
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            goHome(
                                                it.result?.user?.email ?: "",
                                                ProviderType.FACEBOOK
                                            )
                                        } else {
                                            progressBar.visibility = View.GONE
                                            loginViewModel.showAlert(requireContext(), it.exception?.message.toString())
                                        }
                                    }
                            }
                        }

                        override fun onCancel() {
                            progressBar.visibility = View.GONE
                            loginViewModel.showAlert(requireContext(), "Has cancelado la solicitud")
                        }

                        override fun onError(error: FacebookException?) {
                            progressBar.visibility = View.GONE
                            loginViewModel.showAlert(requireContext(), error?.message.toString())
                        }

                    })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun goHome(email: String, provider: ProviderType) {
        loginViewModel.hideKeyboard(requireContext())
        loginViewModel.crearDatabase(requireActivity().application)
        saveStatusLogin(getString(R.string.logueado))

        val bundle = Bundle()
        bundle.putString("email", email)
        bundle.putString("provider", provider.name)
        findNavController().navigate(R.id.action_loginFragmentNew_to_viewPagerFragment, bundle)
        progressBar.visibility = View.GONE
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
}