package com.example.healthc.presentation.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthc.databinding.ItemProfileAllergyBinding
import com.example.healthc.domain.model.auth.Allergy
import com.example.healthc.presentation.profile.adapter.ProfileAllergyAdapter.ProfileAllergyViewHolder

class ProfileAllergyAdapter(
    private val context: Context
) : ListAdapter<Allergy, ProfileAllergyViewHolder>(AllergyDiffCallback){

    companion object {
        val AllergyDiffCallback = object : DiffUtil.ItemCallback<Allergy>() {
            override fun areItemsTheSame(oldItem: Allergy, newItem: Allergy): Boolean {
                return oldItem.allergy == newItem.allergy
            }

            override fun areContentsTheSame(oldItem: Allergy, newItem: Allergy): Boolean {
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
        fun bind(item : Allergy) {
            binding.model = item
            binding.itemAllergyImageView.setImageResource(
                getIdentifier(item.toEnglish().allergy)
            )
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun getIdentifier(allergy: String) =
        context.resources.getIdentifier(
            allergy,
            "drawable",
            context.packageName
        )
    }
