package com.example.pokemons.fragments.fragment.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemons.databinding.FragmentListOfFavoritesPokemonsBinding
import com.example.pokemons.di.dagger.App
import com.example.pokemons.di.dagger.MyViewModelFactory
import com.example.pokemons.fragments.adapters.favorites.AdapterFavorites
import com.example.pokemons.viewmodel.MainViewModel
import javax.inject.Inject

class ListOfFavoritesPokemonsFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentListOfFavoritesPokemonsBinding
    private val viewModel: MainViewModel by viewModels{
        viewModelFactory
    }
    @Inject
    lateinit var viewModelFactory: MyViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListOfFavoritesPokemonsBinding.inflate(inflater)

        (requireActivity().application as App).appComponent.inject(this)
        bindObservers()

        return binding.root
    }

    private fun bindObservers() {
        viewModel.getPokemonsFromDB()

        viewModel.listOfPokemons.observe(viewLifecycleOwner){
            bindRCView()
        }
    }

    private fun bindRCView() {
        val adapter = AdapterFavorites(requireContext())
        binding.rcViewFavorites.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireContext())

            val temp = viewModel.listOfPokemons.value
            if(temp != null)
                adapter.setListOfPokemons(temp)
            else
                assert(false)
        }
    }

}