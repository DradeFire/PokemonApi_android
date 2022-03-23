package com.example.pokemons.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemons.api.repository.Repository
import com.example.pokemons.api.room.PokemonEntity
import com.example.pokemons.api.room.PokemonsDatabase
import com.example.pokemons.json.PokemonJsonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(private val context: Context): ViewModel() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }
    private val repository: Repository by lazy {
        Repository(PokemonsDatabase.getDatabase(context))
    }
    val myResponsePokemon: MutableLiveData<PokemonJsonModel> = MutableLiveData()
    val listOfPokemons: MutableLiveData<List<PokemonEntity>> = MutableLiveData()

    fun getPokemonFromServer(name: String){
        compositeDisposable.add(
            repository.getPokemonFromServerByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                        myResponsePokemon.value = response
                           }, { error ->
                    val mes = error.localizedMessage
                    if(mes == "HTTP 404 ")
                        Toast.makeText(context, "Такого Покемона не существует!", Toast.LENGTH_SHORT).show()
                })

        )
    }

    fun getPokemonsFromDB(){
        compositeDisposable.add(
            repository.getlistOfPokemonsFromDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> listOfPokemons.postValue(response) },
                    {
                            Toast.makeText(context, "Неизвестная ошибка!", Toast.LENGTH_SHORT).show()
                    })
        )
    }

    fun addPokemonToDB(pokemon: PokemonEntity){
        compositeDisposable.add(
            repository.addPokemonToDB(pokemon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ },
                    {
                        Toast.makeText(context, "Неизвестная ошибка!", Toast.LENGTH_SHORT).show()
                    })
        )
    }

    fun deletePokemonFromDB(pokemon: PokemonEntity){
        compositeDisposable.add(
            repository.deletePokemonFromDB(pokemon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

}