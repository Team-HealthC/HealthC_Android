package com.example.healthc.presentation.food.Dish.search_Dish.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemSearchDishBinding
import com.example.healthc.domain.model.food.DishItem

class SearchDishAdapter : ListAdapter<DishItem,
        SearchDishAdapter.SearchDishViewHolder>(DishItemCallback){
    
    companion object {
        val DishItemCallback = object : DiffUtil.ItemCallback<DishItem>(){
            override fun areItemsTheSame(
                oldItem: DishItem, newItem: DishItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DishItem,
                newItem: DishItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchDishViewHolder {
        return SearchDishViewHolder(ItemSearchDishBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: SearchDishViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchDishViewHolder(private val binding : ItemSearchDishBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : DishItem) {
            binding.item = item
        }
    }
}