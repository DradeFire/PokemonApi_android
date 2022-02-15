package com.example.pokemons.fragments.fragment.menu

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokemons.R
import com.example.pokemons.databinding.FragmentMainMenuBinding
import com.example.pokemons.di.dagger.App
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainMenuFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater)

        (requireActivity().applicationContext as App)
            .appComponent.inject(this)
        requireActivity()
            .toolbar_in_activity.visibility = View.VISIBLE

        return binding.root
    }

    @Inject
    fun startAnimation(){
        val animScaleX = ObjectAnimator
            .ofFloat(binding.imLogoMainMenu,"scaleX", 1f, 1.02f,1f)
        animScaleX.apply {
            duration = 1500
            repeatCount = Animation.INFINITE
        }

        val animScaleY = ObjectAnimator
            .ofFloat(binding.imLogoMainMenu,"scaleY", 1f, 1.02f,1f)
        animScaleY.apply {
            duration = 1500
            repeatCount = Animation.INFINITE
        }

        animScaleX.start()
        animScaleY.start()
    }
    @Inject
    fun bindButtons() = with(binding){
        btFromMainMenuToFindPokemonByName.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_findPokemonByNameFragment)
        }
        btFromMainMenuToRandom.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_randomPokemonsFragment)
        }
    }

}