package com.empresa.myapplication.marvelapp._view.personajes

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.local.AppDatabase
import com.empresa.myapplication.marvelapp._model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp._model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.favoritos.FavoriteRepositoryImpl
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaPersonajesAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.FavoritesViewModel
import com.empresa.myapplication.marvelapp._viewmodel.factorys.FavoritosVMFactory
import com.empresa.myapplication.marvelapp._viewmodel.factorys.PersonajeVMFactory
import com.empresa.myapplication.marvelapp._viewmodel.personajes.PersonajesViewModel
import com.empresa.myapplication.marvelapp.databinding.FragmentListaPersonajesBinding
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_personajes.*

class ListaPersonajesFragment : Fragment(), BasicMethods,
    RecyclerViewListaPersonajesAdapter.OnPersonajeClickListener,
    RecyclerViewListaPersonajesAdapter.OnLongPersonajeClickListener {

    // inyectamos las dependencias necesarias
    private val personajesViewModel by viewModels<PersonajesViewModel> {
        PersonajeVMFactory(
            PersonajeRepositoryImpl(
                DataSourceApi()
            )
        )
    }

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

        val binding = DataBindingUtil.inflate<FragmentListaPersonajesBinding>(inflater,
            R.layout.fragment_lista_personajes,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaPj_recyclerView.visibility = View.VISIBLE
        contenedor_sinseñal.visibility = View.GONE

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        // escuchamos el livedata del viewmodel
        // cargamos recycker con la lista entera de personajes que devuelve la api o mostramos error por pantalla con toast
        personajesViewModel.charactersList.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading<*> -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success<*> -> {
                    progressBar.visibility = View.GONE
                    listaPj_recyclerView.adapter =
                        RecyclerViewListaPersonajesAdapter(
                            requireContext(),
                            it.data as List<Result>,
                            this,
                            this
                        )
                }
                is Resource.Failure<*> -> {
                    progressBar.visibility = View.GONE
                    listaPj_recyclerView.visibility = View.GONE
                    contenedor_sinseñal.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error al traer los datos: ${it.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun init() {
        personajesViewModel.getCharacters()
        setupRecyclerView()
    }

    override fun initListeners() {}

    private fun setupRecyclerView() {
        listaPj_recyclerView.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun onPersonajeClick(pj: Result) {
        // guardamos objeto para pasarlo al siguiente fragment como extra
        val bundle = Bundle()
        bundle.putParcelable("personaje", pj)
        findNavController().navigate(
            R.id.action_viewPagerFragment_to_detallePersonajeFragment,
            bundle
        )
    }

    override fun onLongClickPersonajeListener(pj: Result): Boolean {
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
        return true
    }
}