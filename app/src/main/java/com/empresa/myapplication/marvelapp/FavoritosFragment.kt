package com.empresa.myapplication.marvelapp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp._model.local.AppDatabase
import com.empresa.myapplication.marvelapp._model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp._model.local.FavoritosEntity
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoritRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaPersonajesROOMAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.FavoritosViewModel
import com.empresa.myapplication.marvelapp._viewmodel.factorys.FavoritosVMFactory
import com.empresa.myapplication.marvelapp.vo.Resource
import com.facebook.login.LoginManager
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.app_bar_custom.*
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoritosFragment : Fragment(), BasicMethods,
    RecyclerViewListaPersonajesROOMAdapter.OnPersonajeClickListener,
    RecyclerViewListaPersonajesROOMAdapter.OnLongPersonajeClickListener {

    val STATUS_LOGIN = "status_login"

    // inyectamos las dependencias necesarias
    private val favoritosViewModel by viewModels<FavoritosViewModel> {
        FavoritosVMFactory(
            FavoritRepositoryImpl(
                DataSourceRoom(AppDatabase.getInstance(requireActivity().applicationContext))
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favoritos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar?.show()
        title_appbar.text = "FAVORITOS"
        imageView_close_appbar.visibility = View.VISIBLE
        favoritos_imageView_appbar.visibility = View.GONE
        signout_imageView_appbar.visibility = View.GONE

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        favoritosViewModel.favoritosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loafing -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    favoritos_recyclerView_favoritosFragment.adapter =
                        RecyclerViewListaPersonajesROOMAdapter(
                            requireContext(),
                            result.data,
                            this,
                            this
                        )
                    favoritos_recyclerView_favoritosFragment.adapter?.notifyDataSetChanged()

                    if (result.data.size < 1) {
                        sinFavofitos_textView.visibility = View.VISIBLE
                        favoritos_recyclerView_favoritosFragment.visibility = View.GONE
                    } else {
                        sinFavofitos_textView.visibility = View.GONE
                        favoritos_recyclerView_favoritosFragment.visibility = View.VISIBLE
                    }
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    favoritos_recyclerView_favoritosFragment.visibility = View.GONE
                    //contenedor_sinseñal.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error al traer los datos: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun init() {
        setupRecyclerView()
    }

    override fun initListeners() {
        imageView_close_appbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_ViewPagerFrafment)
        }
    }

    override fun onPersonajeClick(pj: FavoritosEntity) {}

    private fun setupRecyclerView() {
        favoritos_recyclerView_favoritosFragment.layoutManager =
            LinearLayoutManager(requireContext())
    }

    override fun onLongClickPersonajeListener(pj: FavoritosEntity, position: Int): Boolean {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar")
        builder.setMessage("Eliminará a este personaje de la lista de favoritos.")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            favoritosViewModel.deleteFavorito(pj)
            favoritos_recyclerView_favoritosFragment.adapter!!.notifyItemRemoved(position)
            favoritos_recyclerView_favoritosFragment.adapter?.notifyDataSetChanged()
            findNavController().navigate(R.id.favoritosFragment)
        })

        val dialog = builder.create()
        dialog.show()
        return true
    }
}