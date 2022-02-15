package com.example.pokemons.fragments.fragment.finding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemons.R
import com.example.pokemons.api.room.PokemonEntity
import com.example.pokemons.databinding.FragmentFindPokemonByNameBinding
import com.example.pokemons.di.dagger.App
import com.example.pokemons.di.dagger.MyViewModelFactory
import com.example.pokemons.fragments.adapters.skills.AdapterSkills
import com.example.pokemons.json.PokemonJsonModel
import com.example.pokemons.json.optionals.Ability
import com.example.pokemons.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FindPokemonByNameFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentFindPokemonByNameBinding
    private val viewModel: MainViewModel by viewModels{
            viewModelFactory
        }
    @Inject
    lateinit var viewModelFactory: MyViewModelFactory
    private lateinit var listOfAbilities: List<Ability>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFindPokemonByNameBinding.inflate(inflater)

        (requireActivity().applicationContext as App)
            .appComponent.inject(this)

        return binding.root
    }

    @Inject
    fun bindObservers(){
        viewModel.myResponsePokemon.observe(viewLifecycleOwner){ response ->
            if(response != null)
                bindViewPokemonItem(response)
        }
    }

    @Inject
    fun bindButtons() = with(binding) {
        btFindPokemonByName.setOnClickListener {
            if(CLPokemonItemFind.visibility == View.VISIBLE)
                CLPokemonItemFind.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.IO) {
                if (inputFindPokemonByName.text.isNotEmpty() or inputFindPokemonByName.text.isNotBlank())
                    viewModel.getPokemonFromServer(inputFindPokemonByName.text.toString())
            }
        }
        pokemonItemFindPokemonByName.btAddToFavorites.setOnClickListener {
            pokemonItemFindPokemonByName.apply {
                addElementToFavorites(
                    PokemonEntity(
                        0,
                        tvPokemonName.text.toString(),
                        tvHpPokemon.text.toString().toInt(),
                        tvAttackPokemon.text.toString().toInt(),
                        tvDefensePokemon.text.toString().toInt(),
                        tvSpecialAttackPokemon.text.toString().toInt(),
                        tvSpecialDefensePokemon.text.toString().toInt(),
                        tvSpeedPokemon.text.toString().toInt(),
                        listOfAbilities
                    )
                )
            }
            Toast.makeText(requireContext(), getString(R.string.successful_added_to_favorites), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.myResponsePokemon.removeObservers(viewLifecycleOwner)
    }

    private fun bindViewPokemonItem(body: PokemonJsonModel) = with(binding.pokemonItemFindPokemonByName) {
        tvPokemonName.text = body.name

        tvHpPokemon.text = body.stats[0].base_stat.toString()
        tvAttackPokemon.text = body.stats[1].base_stat.toString()
        tvDefensePokemon.text = body.stats[2].base_stat.toString()
        tvSpecialAttackPokemon.text = body.stats[3].base_stat.toString()
        tvSpecialDefensePokemon.text = body.stats[4].base_stat.toString()
        tvSpeedPokemon.text = body.stats[5].base_stat.toString()

        val adapter = AdapterSkills()
        rcViewSkills.adapter = adapter
        rcViewSkills.layoutManager = LinearLayoutManager(requireContext())
        listOfAbilities = body.abilities
        adapter.setListOfSkills(body.abilities)

        binding.CLPokemonItemFind.visibility = View.VISIBLE
    }

    private fun addElementToFavorites(pokemon: PokemonEntity){
        viewModel.addPokemonToDB(pokemon)
    }

}