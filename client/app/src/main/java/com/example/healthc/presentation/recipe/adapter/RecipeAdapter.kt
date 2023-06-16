package com.example.healthc.presentation.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemRecipeBinding
import com.example.healthc.domain.model.recipe.Recipe

class RecipeAdapter : ListAdapter<Recipe, RecipeAdapter.RecipeViewHolder>(RecipeCallback){
    companion object {
        val RecipeCallback = object : DiffUtil.ItemCallback<Recipe>(){
            override fun areItemsTheSame(
                oldItem: Recipe, newItem: Recipe
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Recipe,
                newItem: Recipe
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(ItemRecipeBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class RecipeViewHolder(private val binding : ItemRecipeBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Recipe) {
            binding.item = item
        }
    }
}