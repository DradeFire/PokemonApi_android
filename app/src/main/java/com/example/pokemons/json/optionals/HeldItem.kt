package com.example.pokemons.json.optionals

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)