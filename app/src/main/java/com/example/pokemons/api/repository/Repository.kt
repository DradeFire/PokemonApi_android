package com.example.pokemons.api.repository

import com.example.pokemons.api.retrofit.RetrofitInstanceApi
import com.example.pokemons.api.room.PokemonEntity
import com.example.pokemons.api.room.PokemonsDatabase
import com.example.pokemons.json.PokemonJsonModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class Repository @Inject constructor(private var database: PokemonsDatabase) {

    fun getlistOfPokemonsFromDB(): Single<List<PokemonEntity>> {
        return database.pokemonDao().readAllData()
    }

    fun getPokemonFromServerByName(name: String): Observable<PokemonJsonModel>{
        return RetrofitInstanceApi.apiPokemon.getPokemon(name)
    }

    fun addPokemonToDB(pokemon: PokemonEntity): Completable {
        return database.pokemonDao().addPokemon(pokemon)
    }

    fun deletePokemonFromDB(pokemon: PokemonEntity): Completable{
        return database.pokemonDao().deletePokemon(pokemon)
    }
}

