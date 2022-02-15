package com.example.pokemons.fragments.fragment.start

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pokemons.R
import com.example.pokemons.databinding.FragmentStartBinding
import com.example.pokemons.di.dagger.App
import javax.inject.Inject

class StartFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)

        (requireActivity().application as App).appComponent.inject(this)

        return binding.root
    }

    @Inject
    fun startAnimation(){
        val animScaleX = ObjectAnimator
            .ofFloat(binding.imPicachuWithAnim,"scaleX", .8f, 1f, .8f).apply {
                duration = 2000
                repeatCount = 2
            }


        val animScaleY = ObjectAnimator
            .ofFloat(binding.imPicachuWithAnim,"scaleY", .8f, 1f,.8f).apply {
                duration = 2000
                repeatCount = 2
                doOnEnd {
                    changeFragmentInNavGraph()
                }
            }

        animScaleX.start()
        animScaleY.start()
    }

    private fun changeFragmentInNavGraph() = lifecycleScope.launchWhenResumed{
        findNavController().navigate(R.id.action_startFragment_to_mainMenuFragment)
    }

}