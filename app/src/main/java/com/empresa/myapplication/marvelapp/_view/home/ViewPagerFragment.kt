package com.empresa.myapplication.marvelapp._view.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._view.adapters.MyViewPagerAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._view.eventos.EventosFragment
import com.empresa.myapplication.marvelapp._view.personajes.ListaPersonajesFragment
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.app_bar_custom.*
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerFragment : Fragment(), BasicMethods {

    val STATUS_LOGIN = "status_login"

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

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {}

    override fun init() {
        loadViewPager()
    }

    override fun initListeners() {
        signout_imageView_appbar.setOnClickListener {
            signOut()
            //Toast.makeText(requireContext(), "Aca nos deslogueamos", Toast.LENGTH_SHORT).show()
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
                findNavController().navigate(R.id.action_global_LoginFrafment)
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