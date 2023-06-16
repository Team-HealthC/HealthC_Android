package com.example.healthc.presentation.kor_product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemKorProductBinding
import com.example.healthc.domain.model.kor_product.KorProductInfo
import com.example.healthc.presentation.kor_product.adapter.KorProductAdapter.KorProductViewHolder

class KorProductAdapter : ListAdapter<KorProductInfo, KorProductViewHolder>(KorProductInfoCallback){
    
    companion object {
        val KorProductInfoCallback = object : DiffUtil.ItemCallback<KorProductInfo>(){
            override fun areItemsTheSame(
                oldItem: KorProductInfo, newItem: KorProductInfo): Boolean {
                return oldItem.rnum == newItem.rnum
            }

            override fun areContentsTheSame(
                oldItem: KorProductInfo,
                newItem: KorProductInfo
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
        fun bind(item : KorProductInfo) {
            binding.item = item
        }
    }
}