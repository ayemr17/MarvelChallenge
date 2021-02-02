package com.empresa.myapplication.marvelapp

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.empresa.myapplication.marvelapp.model.local.AppDatabase
import com.empresa.myapplication.marvelapp.model.local.DataSourceRoom
import com.empresa.myapplication.marvelapp.model.local.FavoritesEntity
import com.empresa.myapplication.marvelapp.model.repository.favoritos.FavoriteRepositoryImpl
import com.empresa.myapplication.marvelapp.view.adapters.FavoritesAdapter
import com.empresa.myapplication.marvelapp.view.base.BasicMethods
import com.empresa.myapplication.marvelapp.viewModel.favorites.FavoritesViewModel
import com.empresa.myapplication.marvelapp.viewModel.factorys.FavoritosVMFactory
import com.empresa.myapplication.marvelapp.databinding.FragmentFavoritosBinding
import kotlinx.android.synthetic.main.app_bar_custom.*
import java.util.ArrayList

class FavoritosFragment : Fragment(), BasicMethods,
    FavoritesAdapter.OnPersonajeClickListener,
    FavoritesAdapter.OnLongPersonajeClickListener {

    val STATUS_LOGIN = "status_login"
    private lateinit var binding: FragmentFavoritosBinding
    private var favoritesList = ArrayList<FavoritesEntity>()

    // inyectamos las dependencias necesarias
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

        binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_favoritos, container, false)

        binding.viewModel = favoritosViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = FavoritesAdapter(
            requireContext(),
            favoritesList,
            this,
            this
        )
        return binding.root
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

        favoritosViewModel.favoritesList.observe(viewLifecycleOwner, Observer { list ->
            binding.progressBar.visibility = View.GONE
            binding.favoritosRecyclerViewFavoritosFragment.adapter =
                FavoritesAdapter(
                    requireContext(),
                    list,
                    this,
                    this
                )
            binding.favoritosRecyclerViewFavoritosFragment.adapter?.notifyDataSetChanged()

            if (list.isEmpty()) {
                binding.sinFavofitosTextView.visibility = View.VISIBLE
                binding.favoritosRecyclerViewFavoritosFragment.visibility = View.GONE
            } else {
                binding.sinFavofitosTextView.visibility = View.GONE
                binding.favoritosRecyclerViewFavoritosFragment.visibility = View.VISIBLE
            }
        })

        /*favoritosViewModel.favoritosList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.favoritosRecyclerViewFavoritosFragment.adapter =
                        RecyclerViewListaPersonajesROOMAdapter(
                            requireContext(),
                            result.data,
                            this,
                            this
                        )
                    binding.favoritosRecyclerViewFavoritosFragment.adapter?.notifyDataSetChanged()

                    if (result.data.size < 1) {
                        binding.sinFavofitosTextView.visibility = View.VISIBLE
                        binding.favoritosRecyclerViewFavoritosFragment.visibility = View.GONE
                    } else {
                        binding.sinFavofitosTextView.visibility = View.GONE
                        binding.favoritosRecyclerViewFavoritosFragment.visibility = View.VISIBLE
                    }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.favoritosRecyclerViewFavoritosFragment.visibility = View.GONE
                    //contenedor_sinseñal.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Hubo un error al traer los datos: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })*/
    }

    override fun init() {
        favoritosViewModel.getFavoritesList()
        setupRecyclerView()
    }

    override fun initListeners() {
        imageView_close_appbar.setOnClickListener {
            findNavController().navigate(R.id.action_global_ViewPagerFrafment)
        }
    }

    override fun onPersonajeClick(pj: FavoritesEntity) {}

    private fun setupRecyclerView() {
        binding.favoritosRecyclerViewFavoritosFragment.layoutManager =
            LinearLayoutManager(requireContext())
    }

    override fun onLongClickPersonajeListener(pj: FavoritesEntity, position: Int): Boolean {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Eliminar")
        builder.setMessage("Eliminará a este personaje de la lista de favoritos.")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
            favoritosViewModel.deleteFavorite(pj)
            binding.favoritosRecyclerViewFavoritosFragment.adapter!!.notifyItemRemoved(position)
            binding.favoritosRecyclerViewFavoritosFragment.adapter?.notifyDataSetChanged()
            findNavController().navigate(R.id.favoritosFragment)
        })

        val dialog = builder.create()
        dialog.show()
        return true
    }
}