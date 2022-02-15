package com.example.pokemons.api.room

import android.content.Context
import androidx.room.*
import com.example.pokemons.consts.Consts
import com.example.pokemons.json.optionals.Ability
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class AbilityDataConverter {
    @TypeConverter
    fun fromCountryLangList(countryLang: List<Ability?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Ability?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toCountryLangList(countryLangString: String?): List<Ability>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Ability?>?>() {}.type
        return gson.fromJson<List<Ability>>(countryLangString, type)
    }
}

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
@TypeConverters(AbilityDataConverter::class)
abstract class PokemonsDatabase: RoomDatabase() {

    abstract fun pokemonDao(): PokemonsDao

    companion object{
        @Volatile
        var INSTANCE: PokemonsDatabase? = null

        fun getDatabase(context: Context): PokemonsDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null)
                return tempInstance

            synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PokemonsDatabase::class.java,
                    Consts.NAME_OF_DATABASE
                ).build()
                INSTANCE = instance

                return instance
            }

        }
    }
}