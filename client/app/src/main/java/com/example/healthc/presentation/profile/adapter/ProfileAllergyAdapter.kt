package com.example.healthc.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemProfileAllergyBinding
import com.example.healthc.presentation.profile.adapter.ProfileAllergyAdapter.ProfileAllergyViewHolder

class ProfileAllergyAdapter : ListAdapter<String, ProfileAllergyViewHolder>(AllergyDiffCallback){

    companion object {
        val AllergyDiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.length == newItem.length
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAllergyViewHolder {
        return ProfileAllergyViewHolder(ItemProfileAllergyBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ProfileAllergyViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ProfileAllergyViewHolder(private val binding: ItemProfileAllergyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(allergy : String) {
            binding.itemAllergyTextView.text = allergy
        }
    }
}