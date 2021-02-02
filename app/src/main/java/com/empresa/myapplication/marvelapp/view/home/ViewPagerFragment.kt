package com.empresa.myapplication.marvelapp.view.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp.view.adapters.MyViewPagerAdapter
import com.empresa.myapplication.marvelapp.view.base.BasicMethods
import com.empresa.myapplication.marvelapp.view.eventos.EventosFragment
import com.empresa.myapplication.marvelapp.view.personajes.ListaPersonajesFragment
import com.empresa.myapplication.marvelapp.util.ProviderType
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.app_bar_custom.*
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment : Fragment(), BasicMethods {

    val STATUS_LOGIN = "status_login"
    private var email: String = ""
    private var provider: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar!!.title =
            getString(R.string.app_name)
        (activity as AppCompatActivity?)!!.supportActionBar?.show()

        // capturamos los extras enviiados por el fragment anterior y lo convertimos nuevamente en objeto para manipularlo
        try {
            requireArguments().let {
                email = it.getString("email", "")
                provider = it.getString("provider", "")
            }
        } catch (e: Exception) {
            Log.e("getArgumentsViewPage", "onViewCreated: ${e.message}")
        }

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {}

    override fun init() {
        loadViewPager()
    }

    override fun initListeners() {
        favoritos_imageView_appbar.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_favoritosFragment)
        }
        signout_imageView_appbar.setOnClickListener {
            signOut()
        }
    }

    fun loadViewPager() {
        val fragmentList = arrayListOf<Fragment>(
            ListaPersonajesFragment(),
            EventosFragment()
        )
        val adapter =
            MyViewPagerAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPagerTabBar.adapter = adapter

        var tabLayoutMediator = TabLayoutMediator(
            tabBar,
            viewPagerTabBar,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Personajes"
                        tab.setIcon(R.drawable.ic_ironman)
                    }
                    1 -> {
                        tab.text = "Eventos"
                        tab.setIcon(R.drawable.ic_eventos)
                    }
                }
            })
        tabLayoutMediator.attach()
    }

    private fun signOut() {
        if (provider.equals(ProviderType.FACEBOOK.name)) {
            try {
                LoginManager.getInstance().logOut()
            } catch (e:Exception) {
                Log.e("logOffFacebook", "signOut: ${e.message}")
            }
        }

        AuthUI.getInstance()
            .signOut(requireActivity().applicationContext)
            .addOnCompleteListener {
                // se cambia el status de login guardado en las preferencias
                val sharedPref = activity?.getSharedPreferences(
                    getString(R.string.preferencias), Context.MODE_PRIVATE
                )
                    ?: return@addOnCompleteListener
                with(sharedPref.edit()) {
                    putString(STATUS_LOGIN, getString(R.string.deslogueado))
                    commit()
                }

                findNavController().navigate(R.id.action_global_LoginFrafmentNew)
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