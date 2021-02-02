package com.empresa.myapplication.marvelapp._view.eventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.ListComicsAdapter
import com.empresa.myapplication.marvelapp._view.adapters.EventsAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.eventos.EventosViewModel
import com.empresa.myapplication.marvelapp._viewmodel.factorys.EventosVMFactory
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.progressBar
import kotlinx.android.synthetic.main.item_recyclerview_eventos.view.*

class EventosFragment : Fragment(), BasicMethods,
    EventsAdapter.OnEventoClickListener {

    // inyectamos las dependencias necesarias
    private val eventosViewModel by viewModels<EventosViewModel> {
        EventosVMFactory(
            EventosRepositoryImpl(
                DataSourceApi()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_eventos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservables()
        init()
        initListeners()
    }

    override fun initObservables() {
        // escuchamos el livedata del viewModel para traer datos de la API
        eventosViewModel.eventosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading<*> -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success<*> -> {
                    progressBar.visibility = View.GONE

                    //cargamos el adapter para mostrar la lista de eventos
                    listEventos_recyclerView.adapter =
                        EventsAdapter(requireContext(),
                            result.data as List<ResultEventos>, this)
                }
                is Resource.Failure<*> -> {
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
        eventosViewModel.getEvents()
        setupRecyclerView()
    }

    override fun initListeners() {}

    private fun setupRecyclerView() {
        listEventos_recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onEventoClick(evento: ResultEventos, view : View) {

        view.listComics_recyclerView_eventos.adapter =
            ListComicsAdapter(requireContext(), evento.comics?.items!!)

        if (evento.comics?.items!!.isEmpty()) {
            view.texto_title_comics.text = getString(R.string.sin_comics)
        }

        if (view.expandableLayout.visibility == View.VISIBLE) {
            view.ic_flechita_evento.setImageResource(R.drawable.ic_bottom)
            view.expandableLayout.visibility = View.GONE
        } else {
            view.ic_flechita_evento.setImageResource(R.drawable.ic_top)
            view.expandableLayout.visibility = View.VISIBLE
        }
    }
}