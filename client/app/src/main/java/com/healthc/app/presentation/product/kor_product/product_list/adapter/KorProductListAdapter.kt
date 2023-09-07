package com.healthc.app.presentation.product.kor_product.product_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.healthc.app.databinding.ItemKorProductBinding
import com.healthc.domain.model.kor_product.KorProduct
import com.healthc.app.presentation.product.kor_product.product_list.adapter.KorProductListAdapter.KorProductViewHolder

class KorProductListAdapter(
    private val onItemClick: (String) -> Unit
): ListAdapter<KorProduct, KorProductViewHolder>(KorProductCallback){
    
    companion object {
        val KorProductCallback = object : DiffUtil.ItemCallback<KorProduct>(){
            override fun areItemsTheSame(
                oldItem: KorProduct, newItem: KorProduct): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: KorProduct,
                newItem: KorProduct
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KorProductViewHolder {
        return KorProductViewHolder(
            ItemKorProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: KorProductViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class KorProductViewHolder(private val binding : ItemKorProductBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : KorProduct) {
            binding.item = item
            binding.cdKorProductItem.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }
}