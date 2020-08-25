package com.empresa.myapplication.marvelapp

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp._view.base.BaseActivity
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._view.home.ViewPagerFragment
import com.empresa.myapplication.marvelapp._view.login.LoginFragment
import com.empresa.myapplication.marvelapp._view.splash.SplashFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_main.view.signOut_Button_activityMain
import kotlinx.android.synthetic.main.app_bar_custom.*
import kotlinx.android.synthetic.main.app_bar_custom.view.*

class MainActivity : BaseActivity(), BasicMethods {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         val toolbar : Toolbar = findViewById(R.id.ActivityMainToolbar)
        setSupportActionBar(toolbar)

        init()
        initListeners()
    }

    override fun initObservables() {

    }

    override fun init() {

    }

    override fun initListeners() {
        signout_imageView_appbar.setOnClickListener {
            //signOut()

            Toast.makeText(this, "Aca nos deslogueamos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {

        val fragment: List<Fragment> = nav_host_fragment.childFragmentManager.fragments
        if (fragment.isNotEmpty()) {
            if (fragment[0] is SplashFragment) {
                super.onBackPressed()
            } else if (fragment[0] is LoginFragment) {
                super.onBackPressed()
            } else if (fragment[0] is ViewPagerFragment) {
                super.onBackPressed()
            } else {
                // No fue la mejor solucion pero fue la unica que encontre que me funcionara despues de renegar tanto
                findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_ViewPagerFrafment)
            }
        }
    }
}