package com.example.healthc.presentation.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemProfileDiseaseBinding
import com.example.healthc.presentation.profile.adapter.ProfileDiseaseAdapter.ProfileDiseaseViewHolder

class ProfileDiseaseAdapter : ListAdapter<String, ProfileDiseaseViewHolder>(AllergyDiffCallback){

    companion object{
        val AllergyDiffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.length == newItem.length
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileDiseaseViewHolder {
        return ProfileDiseaseViewHolder(ItemProfileDiseaseBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: ProfileDiseaseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ProfileDiseaseViewHolder(private val binding: ItemProfileDiseaseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disease : String) {
            binding.itemDiseaseTextView.text = disease
        }
    }
}