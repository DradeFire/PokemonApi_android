package com.example.pokemons.fragments.adapters.skills

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemons.databinding.ItemSkillBinding
import com.example.pokemons.json.optionals.Ability

class AdapterSkills: RecyclerView.Adapter<AdapterSkills.ItemViewHolder>() {

    private var listOfSkills = emptyList<Ability>()

    class ItemViewHolder(private val binding: ItemSkillBinding)
        :RecyclerView.ViewHolder(binding.root) {

        fun bind(ability: Ability){
            binding.tvSkillItem.text = ability.ability.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemSkillBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = listOfSkills[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listOfSkills.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setListOfSkills(abilityList: List<Ability>){
        listOfSkills = abilityList
        notifyDataSetChanged()
    }

}