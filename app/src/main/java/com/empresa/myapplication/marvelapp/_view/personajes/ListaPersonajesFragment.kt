package com.empresa.myapplication.marvelapp._view.personajes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.remote.DataSource
import com.empresa.myapplication.marvelapp._model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp._model.repository.personajes.PersonajeRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaPersonajesAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.factorys.PersonajeVMFactory
import com.empresa.myapplication.marvelapp._viewmodel.personajes.PersonajesViewModel
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_lista_personajes.*

class ListaPersonajesFragment : Fragment(), BasicMethods, RecyclerViewListaPersonajesAdapter.OnPersonajeClickListener {

    private val personajesViewModel by viewModels<PersonajesViewModel> {
        PersonajeVMFactory(
            PersonajeRepositoryImpl(
                DataSource()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_personajes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        personajesViewModel.personajesList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loafing -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    listaPj_recyclerView.adapter =
                        RecyclerViewListaPersonajesAdapter(requireContext(), result.data, this)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
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

    override fun initListeners() {}

    private fun setupRecyclerView() {
        listaPj_recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onPersonajeClick(pj: Result) {
        val bundle = Bundle()
        bundle.putParcelable("personaje", pj)
        findNavController().navigate(R.id.action_viewPagerFragment_to_detallePersonajeFragment, bundle)
    }
}