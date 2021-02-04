package com.empresa.myapplication.marvelapp.view.personajes

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.R
import com.empresa.myapplication.marvelapp.databinding.FragmentListaPersonajesBinding
import com.empresa.myapplication.marvelapp.model.local.AppDatabase
import com.empresa.myapplication.marvelapp.model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp.model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp.model.remote.DataSourceApi
import com.empresa.myapplication.marvelapp.model.remote.pojos.personajes.Result
import com.empresa.myapplication.marvelapp.model.repository.favoritos.FavoriteRepositoryImpl
import com.empresa.myapplication.marvelapp.model.repository.personajes.PersonajeRepositoryImpl
import com.empresa.myapplication.marvelapp.view.adapters.CharactersAdapter
import com.empresa.myapplication.marvelapp.view.base.BasicMethods
import com.empresa.myapplication.marvelapp.viewModel.factorys.FavoritosVMFactory
import com.empresa.myapplication.marvelapp.viewModel.factorys.PersonajeVMFactory
import com.empresa.myapplication.marvelapp.viewModel.favorites.FavoritesViewModel
import com.empresa.myapplication.marvelapp.viewModel.personajes.PersonajesViewModel
import com.empresa.myapplication.marvelapp.vo.Resource

class ListaPersonajesFragment : Fragment(), BasicMethods,
    CharactersAdapter.OnPersonajeClickListener,
    CharactersAdapter.OnLongPersonajeClickListener {

    private var _binding: FragmentListaPersonajesBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentListaPersonajesBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listaPjRecyclerView.visibility = View.VISIBLE
        binding.noSignalContainer.visibility = View.GONE

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
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success<*> -> {
                    binding.progressBar.visibility = View.GONE
                    binding.listaPjRecyclerView.adapter =
                        CharactersAdapter(
                            requireContext(),
                            it.data as List<Result>,
                            this,
                            this
                        )
                }
                is Resource.Failure<*> -> {
                    binding.progressBar.visibility = View.GONE
                    binding.listaPjRecyclerView.visibility = View.GONE
                    binding.noSignalContainer.visibility = View.VISIBLE
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
        binding.listaPjRecyclerView.layoutManager = LinearLayoutManager(requireContext())


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}