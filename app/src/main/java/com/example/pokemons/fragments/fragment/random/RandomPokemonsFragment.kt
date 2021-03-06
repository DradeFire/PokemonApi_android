package com.example.pokemons.fragments.fragment.random

import android.annotation.SuppressLint
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
import com.example.pokemons.databinding.FragmentRandomPokemonsBinding
import com.example.pokemons.di.dagger.App
import com.example.pokemons.di.dagger.MyViewModelFactory
import com.example.pokemons.fragments.adapters.skills.AdapterSkills
import com.example.pokemons.json.PokemonJsonModel
import com.example.pokemons.json.optionals.Ability
import com.example.pokemons.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class RandomPokemonsFragment @Inject constructor(): Fragment() {

    private lateinit var binding: FragmentRandomPokemonsBinding
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
        binding = FragmentRandomPokemonsBinding.inflate(inflater)

        (requireActivity().applicationContext as App).appComponent.inject(this)

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
    fun bindButtons() = with(binding)  {
        btRandomPokemon.setOnClickListener {
            if(CLPokemonItemRandom.visibility == View.VISIBLE)
                CLPokemonItemRandom.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.getPokemonFromServer(randomizePokemonChoosing().toString())
            }

        }

        pokemonItemRandomPokemons.btAddToFavorites.setOnClickListener {
            pokemonItemRandomPokemons.apply {
                addElementToFavorites(
                    PokemonEntity(
                        0,
                        tvPokemonName.text.toString(),
                        tvHpPokemon.text.toString().substring(3) .toInt(),
                        tvAttackPokemon.text.toString().substring(7).toInt(),
                        tvDefensePokemon.text.toString().substring(8).toInt(),
                        tvSpecialAttackPokemon.text.toString().substring(14).toInt(),
                        tvSpecialDefensePokemon.text.toString().substring(15).toInt(),
                        tvSpeedPokemon.text.toString().substring(6).toInt(),
                        listOfAbilities
                    )
                )
            }
            Toast.makeText(requireContext(), getString(R.string.successful_added_to_favorites), Toast.LENGTH_SHORT).show()
        }
    }



    @SuppressLint("SetTextI18n")
    private fun bindViewPokemonItem(body: PokemonJsonModel) = with(binding.pokemonItemRandomPokemons) {
        tvPokemonName.text = body.name

        tvHpPokemon.text = "HP ${body.stats[0].base_stat}"
        tvAttackPokemon.text = "Attack ${body.stats[1].base_stat}"
        tvDefensePokemon.text = "Defense ${body.stats[2].base_stat}"
        tvSpecialAttackPokemon.text = "SpecialAttack ${body.stats[3].base_stat}"
        tvSpecialDefensePokemon.text = "SpecialDefense ${body.stats[4].base_stat}"
        tvSpeedPokemon.text = "Speed ${body.stats[5].base_stat}"

        val adapter = AdapterSkills()
        rcViewSkills.adapter = adapter
        rcViewSkills.layoutManager = LinearLayoutManager(requireContext())
        listOfAbilities = body.abilities
        adapter.setListOfSkills(listOfAbilities)

        binding.CLPokemonItemRandom.visibility = View.VISIBLE
    }

    private fun addElementToFavorites(pokemon: PokemonEntity){
        viewModel.addPokemonToDB(pokemon)
    }

    override fun onPause() {
        super.onPause()
        viewModel.myResponsePokemon.removeObservers(viewLifecycleOwner)
    }

    private fun randomizePokemonChoosing(): Int =
        when(val randomInt = Random.nextInt(1, 950)){
            in(899..950) -> Random.nextInt(10001,10220)
            else -> randomInt
        }

}