package com.example.pokemons.api.room

import androidx.room.*
import com.example.pokemons.consts.Consts
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface PokemonsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPokemon(pokemon: PokemonEntity): Completable

    @Delete
    fun deletePokemon(pokemon: PokemonEntity): Completable

    @Query("SELECT * FROM ${Consts.NAME_OF_DB_TABLE} ORDER BY id ASC")
    fun readAllData(): Single<List<PokemonEntity>>

    @Query("DELETE FROM ${Consts.NAME_OF_DB_TABLE}")
    fun deleteAll()

}