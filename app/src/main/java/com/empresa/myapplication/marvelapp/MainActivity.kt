package com.empresa.myapplication.marvelapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.empresa.myapplication.marvelapp.databinding.ActivityMainBinding
import com.empresa.myapplication.marvelapp.view.base.BaseActivity
import com.empresa.myapplication.marvelapp.view.base.BasicMethods
import com.empresa.myapplication.marvelapp.view.home.ViewPagerFragment
import com.empresa.myapplication.marvelapp.view.login.LoginFragmentNew
import com.empresa.myapplication.marvelapp.view.splash.SplashFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.free.activity_main.*
import kotlinx.android.synthetic.main.activity_main.nav_host_fragment
import kotlinx.android.synthetic.main.app_bar_custom.*

class MainActivity : BaseActivity(), BasicMethods {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val toolbar : Toolbar = findViewById(R.id.ActivityMainToolbar)
        setSupportActionBar(toolbar)

        init()
        initListeners()
    }

    override fun initObservables() {
    }

    override fun init() {
        if (BuildConfig.FLAVOR == "free") {
            MobileAds.initialize(this) {}

            // aca hacemos que cargue el anuncio
            adView.loadAd(AdRequest.Builder().build())
        }
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
            } else if (fragment[0] is LoginFragmentNew) {
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