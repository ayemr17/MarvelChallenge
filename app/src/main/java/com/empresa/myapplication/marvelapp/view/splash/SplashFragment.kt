package com.empresa.myapplication.marvelapp.view.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.empresa.myapplication.marvelapp.R
import kotlinx.android.synthetic.main.fragment_splash.*
import java.util.*


class SplashFragment : Fragment() {

    private lateinit var auth: String
    val STATUS_LOGIN = "status_login"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // escondemos actionBar
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()

        // consultamos estado de logueo en preference para ir a pantalla principal o al login con firebase
        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.preferencias), Context.MODE_PRIVATE)
        auth = sharedPref?.getString(STATUS_LOGIN, getString(R.string.deslogueado))!!
        launchAnimation()
    }

    // para generar animacion transitoria de splash
    private fun launchAnimation() {
        Glide.with(this)
            .load(R.drawable.marvel_gif)
            .diskCacheStrategy(
                DiskCacheStrategy.RESOURCE
            )
            .into(contenedor_imagenSplash)


        Timer().schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    kotlin.run {
                        launchLoginOrHome()
                    }
                }
            }
        }, 3000)
    }

    // verificamos si ya esta logueado o no y en base a eso vamos a login o home
    private fun launchLoginOrHome() {
        if (auth.equals("deslogueado")) {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragmentNew)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
        }
    }
}