package com.healthc.app.presentation.product.product.product_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthc.app.databinding.ItemProductBinding
import com.healthc.domain.model.product.ProductItem

class ProductListAdapter(
    private val onItemClick : (Int) -> Unit
) : ListAdapter<ProductItem, ProductListAdapter.ProductListViewHolder>(ProductItemCallback){
    
    companion object {
        val ProductItemCallback = object : DiffUtil.ItemCallback<ProductItem>(){
            override fun areItemsTheSame(
                oldItem: ProductItem, newItem: ProductItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductItem,
                newItem: ProductItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ProductListViewHolder(private val binding : ItemProductBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ProductItem) {
            binding.item = item
            binding.root.setOnClickListener{
                onItemClick(item.id)
            }
        }
    }
}