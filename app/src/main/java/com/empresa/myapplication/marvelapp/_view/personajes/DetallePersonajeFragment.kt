package com.empresa.myapplication.marvelapp._view.personajes

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.local.AppDatabase
import com.empresa.myapplication.marvelapp._model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp._model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoriteRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaComicsAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.favorites.FavoritesViewModel
import com.empresa.myapplication.marvelapp._viewmodel.factorys.FavoritosVMFactory
import kotlinx.android.synthetic.main.app_bar_custom.*
import kotlinx.android.synthetic.main.fragment_detalle_personaje.*

class DetallePersonajeFragment : Fragment(), BasicMethods {

    val STATUS_LOGIN = "status_login"
    private lateinit var pj: Result

    private val favoritosViewModel by viewModels<FavoritesViewModel> {
        FavoritosVMFactory(
            FavoriteRepositoryImpl(
                DataSourceRoom(AppDatabase.getInstance(requireActivity().applicationContext))
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // capturamos los extras enviiados por el fragment anterior y lo convertimos nuevamente en objeto para manipularlo
        requireArguments().let {
            pj = it.getParcelable("personaje")!!
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_personaje, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)!!.supportActionBar?.show()

        // cambiamos titulo de actionBar y hacemos visible imagen de X
        title_appbar.text = pj.name?.toUpperCase()
        imageView_close_appbar.visibility = View.VISIBLE
        favoritos_imageView_appbar.visibility = View.GONE
        signout_imageView_appbar.visibility = View.GONE

        // cargamos todos los datos que nos trajo el objeto para mostrarlos en pantalla
        var options: RequestOptions = RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        var uri = pj.thumbnail?.path + "." + pj.thumbnail?.extension

        Glide.with(requireContext())
            .load(Uri.parse(uri))
            //.apply(options)
            .into(fotoPJ_imageView_detalle)
        if (pj.description.equals("")) {
            descripcionPJ_textView_detalle.text = getString(R.string.sin_descripcion)
        } else {
            descripcionPJ_textView_detalle.text = pj.description
        }

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {}

    override fun init() {
        setupRecyclerView()
        loadAdapter()
    }

    override fun initListeners() {
        imageView_close_appbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_ViewPagerFrafment)
        }

        contenedor_detalle_personaje.setOnLongClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Agregar")
            builder.setMessage("Agregará este personaje a la lista de favoritos.")
            builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                favoritosViewModel.insertFavorite(
                    FavoritesEntity(pj.id!!.toInt(), pj.name.toString(), pj.description.toString(),
                        pj.thumbnail?.path + "." + (pj.thumbnail?.extension)
                    )
                )
            })

            val dialog = builder.create()
            dialog.show()
            return@setOnLongClickListener true
        }
    }

    private fun setupRecyclerView() {
        // iniciamos recycler y le agregamos separador entre items
        listComic_recyclerView_detalle.layoutManager = LinearLayoutManager(requireContext())
        listComic_recyclerView_detalle.isNestedScrollingEnabled = false
        listComic_recyclerView_detalle.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun loadAdapter() {
        // cargamos el recycler
        listComic_recyclerView_detalle.adapter =
            RecyclerViewListaComicsAdapter(requireContext(), pj.comics?.items!!)
    }


}