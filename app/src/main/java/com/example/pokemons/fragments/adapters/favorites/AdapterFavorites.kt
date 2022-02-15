package com.example.pokemons.fragments.adapters.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons.api.room.PokemonEntity
import com.example.pokemons.databinding.PokemonItemBinding
import com.example.pokemons.fragments.adapters.skills.AdapterSkills
import javax.inject.Inject

class AdapterFavorites @Inject constructor(
    val context: Context)
    : RecyclerView.Adapter<AdapterFavorites.ItemViewHolder>() {

    private var listOfPokemons = emptyList<PokemonEntity>()

    class ItemViewHolder(private val binding: PokemonItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pokemon: PokemonEntity, context: Context) = with(binding){
            tvPokemonName.text = pokemon.name

            tvHpPokemon.text = "HP ${pokemon.hp}"
            tvAttackPokemon.text = "Attack ${pokemon.attack}"
            tvDefensePokemon.text = "Defense ${pokemon.defense}"
            tvSpecialAttackPokemon.text = "SpecialAttack ${pokemon.specialAttack}"
            tvSpecialDefensePokemon.text = "SpecialDefense ${pokemon.specialDefense}"
            tvSpeedPokemon.text = "Speed ${pokemon.speed}"

            val adapter = AdapterSkills()
            rcViewSkills.adapter = adapter
            rcViewSkills.layoutManager = LinearLayoutManager(context)
            adapter.setListOfSkills(pokemon.abilities)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(PokemonItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listOfPokemons[position]
        holder.bind(item, context = context)
    }

    override fun getItemCount(): Int {
        return listOfPokemons.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListOfPokemons(pokemonList: List<PokemonEntity>){
        listOfPokemons = pokemonList
        notifyDataSetChanged()
    }

}