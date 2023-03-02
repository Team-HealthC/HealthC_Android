package com.example.healthc.presentation.food.search_ingredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemSearchIngredientBinding
import com.example.healthc.domain.model.food.Ingredient
import com.example.healthc.presentation.food.search_ingredient.adapter.SearchIngredientAdapter.SearchIngredientViewHolder

class SearchIngredientAdapter : ListAdapter<Ingredient, SearchIngredientViewHolder>(IngredientCallback){
    
    companion object {
        val IngredientCallback = object : DiffUtil.ItemCallback<Ingredient>(){
            override fun areItemsTheSame(
                oldItem: Ingredient, newItem: Ingredient): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Ingredient,
                newItem: Ingredient
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchIngredientViewHolder {
        return SearchIngredientViewHolder(ItemSearchIngredientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: SearchIngredientViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchIngredientViewHolder(private val binding : ItemSearchIngredientBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : Ingredient) {
            binding.item = item
        }
    }
}