package com.example.pokemons.api.retrofit

import com.example.pokemons.json.PokemonJsonModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofit {

    @GET("api/v2/pokemon/{name}")
    fun getPokemon(@Path("name") nameOfPokemon: String): Observable<PokemonJsonModel>

}