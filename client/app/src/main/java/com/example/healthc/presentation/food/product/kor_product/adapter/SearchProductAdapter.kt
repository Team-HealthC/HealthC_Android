package com.example.healthc.presentation.food.product.kor_product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemSearchProductBinding
import com.example.healthc.domain.model.food.ProductItem
import com.example.healthc.presentation.food.product.kor_product.adapter.SearchProductAdapter.SearchProductViewHolder

class SearchProductAdapter : ListAdapter<ProductItem, SearchProductViewHolder>(ProductItemCallback){
    
    companion object {
        val ProductItemCallback = object : DiffUtil.ItemCallback<ProductItem>(){
            override fun areItemsTheSame(
                oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.rnum == newItem.rnum
            }

            override fun areContentsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder {
        return SearchProductViewHolder(
            ItemSearchProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchProductViewHolder(private val binding : ItemSearchProductBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ProductItem) {
            binding.item = item
        }
    }
}