package com.empresa.myapplication.marvelapp._view.eventos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp._model.remote.DataSource
import com.empresa.myapplication.marvelapp._model.remote.pojos.eventos.ResultEventos
import com.empresa.myapplication.marvelapp._model.repository.eventos.EventosRepositoryImpl
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListComicsAdapter
import com.empresa.myapplication.marvelapp._view.adapters.RecyclerViewListaEventosAdapter
import com.empresa.myapplication.marvelapp._view.base.BasicMethods
import com.empresa.myapplication.marvelapp._viewmodel.eventos.EventosViewModel
import com.empresa.myapplication.marvelapp._viewmodel.factorys.EventosVMFactory
import com.empresa.myapplication.marvelapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_eventos.*
import kotlinx.android.synthetic.main.fragment_eventos.progressBar
import kotlinx.android.synthetic.main.item_recyclerview_eventos.*
import kotlinx.android.synthetic.main.item_recyclerview_eventos.view.*

class EventosFragment : Fragment(), BasicMethods,
    RecyclerViewListaEventosAdapter.OnEventoClickListener {

    private val eventosViewModel by viewModels<EventosViewModel> {
        EventosVMFactory(
            EventosRepositoryImpl(
                DataSource()
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
        eventosViewModel.eventosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loafing -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE

                    //cargamos el adapter para mostrar la lista de eventos
                    listEventos_recyclerView.adapter =
                        RecyclerViewListaEventosAdapter(requireContext(), result.data, this)
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
        listEventos_recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onEventoClick(evento: ResultEventos, view : View) {
        // cargamos la lista con los comics que pertenesen al evento seleccionado y desplegamos el item

        view.listComics_recyclerView_eventos.adapter =
            RecyclerViewListComicsAdapter(requireContext(), evento.comics?.items!!)

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