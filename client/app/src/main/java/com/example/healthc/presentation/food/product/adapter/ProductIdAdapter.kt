package com.example.healthc.presentation.food.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemProductIdBinding
import com.example.healthc.domain.model.food.ProductId

class ProductIdAdapter(
    private val onItemClick : (Int) -> Unit
) : ListAdapter<ProductId,
        ProductIdAdapter.ProductIdViewHolder>(ProductItemCallback){
    
    companion object {
        val ProductItemCallback = object : DiffUtil.ItemCallback<ProductId>(){
            override fun areItemsTheSame(
                oldItem: ProductId, newItem: ProductId): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductId,
                newItem: ProductId
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductIdViewHolder {
        return ProductIdViewHolder(
            ItemProductIdBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ProductIdViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ProductIdViewHolder(private val binding : ItemProductIdBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ProductId) {
            binding.item = item
            binding.root.setOnClickListener{
                onItemClick(item.id)
            }
        }
    }
}