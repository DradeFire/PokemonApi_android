package com.example.pokemons.api.retrofit

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceApi {

    private val retrofitPokemon by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiPokemon: ApiRetrofit by lazy {
        retrofitPokemon.create(ApiRetrofit::class.java)
    }

}