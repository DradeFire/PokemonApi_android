 package com.example.pokemons

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.navigation.findNavController
import com.example.pokemons.databinding.ActivityMainBinding
import com.example.pokemons.di.dagger.App
import javax.inject.Inject

 class MainActivity @Inject constructor() : AppCompatActivity() {

     private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (applicationContext as App).appComponent.inject(this)
    }

     @SuppressLint("RtlHardcoded")
     @Inject
     fun setupNavigationFragment() = with(binding){
         toolbarInActivity.btOpenFavoritesFragment.setOnClickListener {
             findNavController(R.id.frag_view).navigate(R.id.listOfFavoritesPokemonsFragment)
         }
         navViewInActivity.setNavigationItemSelectedListener {
             when(it.itemId){
                 R.id.nav_to_find_pokemon -> {
                     findNavController(R.id.frag_view).navigate(R.id.findPokemonByNameFragment)
                     drawerLayout.closeDrawer(Gravity.LEFT, true)
                 }
                 R.id.nav_to_random_pokemon -> {
                     findNavController(R.id.frag_view).navigate(R.id.randomPokemonsFragment)
                     drawerLayout.closeDrawer(Gravity.LEFT, true)
                 }
             }
             true
         }

     }

}