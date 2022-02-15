package com.example.pokemons.api.room

import androidx.room.Entity
import com.example.pokemons.consts.Consts
import com.example.pokemons.json.optionals.Ability

@Entity(tableName = Consts.NAME_OF_DB_TABLE, primaryKeys = ["id", "name"])
data class PokemonEntity (
    val id: Int,
    val name: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val specialAttack: Int,
    val specialDefense: Int,
    val speed: Int,
    val abilities: List<Ability>
)