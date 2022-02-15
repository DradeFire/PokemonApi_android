package com.example.pokemons.di.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokemons.MainActivity
import com.example.pokemons.api.repository.Repository
import com.example.pokemons.api.room.PokemonsDao
import com.example.pokemons.api.room.PokemonsDatabase
import com.example.pokemons.fragments.fragment.favorites.ListOfFavoritesPokemonsFragment
import com.example.pokemons.fragments.fragment.finding.FindPokemonByNameFragment
import com.example.pokemons.fragments.fragment.menu.MainMenuFragment
import com.example.pokemons.fragments.fragment.random.RandomPokemonsFragment
import com.example.pokemons.fragments.fragment.start.StartFragment
import com.example.pokemons.viewmodel.MainViewModel
import dagger.*
import javax.inject.Inject
import javax.inject.Singleton


open class App: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .factory()
            .application(applicationContext)
    }

}

@Suppress("UNCHECKED_CAST")
@Singleton
class MyViewModelFactory @Inject constructor(private val context: Context) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Singleton
@Component(modules = [AllModules::class])
interface AppComponent {

    fun inject(app: Application)
    fun inject(activity: MainActivity)
    fun inject(viewModel: MainViewModel)
    fun inject(startFragment: StartFragment)
    fun inject(mainMenuFragment: MainMenuFragment)
    fun inject(findPokemonByNameFragment: FindPokemonByNameFragment)
    fun inject(randomPokemonsFragment: RandomPokemonsFragment)
    fun inject(listOfFavoritesPokemonsFragment: ListOfFavoritesPokemonsFragment)

    @Component.Factory
    interface Factory{
        fun application(@BindsInstance context: Context): AppComponent
//        @BindsInstance
//        fun context(context: Context): Builder
//
//        @BindsInstance
//        fun database(database: PokemonsDatabase): Builder
    }

}

@Module(includes = [DataBaseModule::class, ViewModelModule::class])
class AllModules

@Module
class ViewModelModule{
    @Singleton
    @Provides
    fun getViewModel(activity: MainActivity): MainViewModel =
        ViewModelProvider(activity)[MainViewModel::class.java]
}

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun dataBaseModule(application: Application): PokemonsDatabase =
        PokemonsDatabase.getDatabase(application)

    @Provides
    @Singleton
    fun provideTaskDao(database: PokemonsDatabase): PokemonsDao =
        database.pokemonDao()

    @Provides
    @Singleton
    fun provideTaskRepository(database: PokemonsDatabase): Repository =
        Repository(database)
}