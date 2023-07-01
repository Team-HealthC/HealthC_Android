package com.example.healthc.presentation.product.product.product_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemProductIdBinding
import com.example.healthc.domain.model.product.ProductIdInfo

class ProductListAdapter(
    private val onItemClick : (Int) -> Unit
) : ListAdapter<ProductIdInfo, ProductListAdapter._ProductListViewHolder>(ProductItemCallback){
    
    companion object {
        val ProductItemCallback = object : DiffUtil.ItemCallback<ProductIdInfo>(){
            override fun areItemsTheSame(
                oldItem: ProductIdInfo, newItem: ProductIdInfo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductIdInfo,
                newItem: ProductIdInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): _ProductListViewHolder {
        return _ProductListViewHolder(
            ItemProductIdBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: _ProductListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class _ProductListViewHolder(private val binding : ItemProductIdBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ProductIdInfo) {
            binding.item = item
            binding.root.setOnClickListener{
                onItemClick(item.id)
            }
        }
    }
}