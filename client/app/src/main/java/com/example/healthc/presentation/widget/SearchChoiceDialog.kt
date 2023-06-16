package com.example.healthc.presentation.widget

import android.content.Context
import android.os.Bundle
import com.example.healthc.databinding.DialogSearchChoiceBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class SearchChoiceDialog(
    context : Context,
    private val onSearchProduct : () -> Unit,
    private val onSearchIngredient : () -> Unit,
    private val onSearchKorProduct : () -> Unit,
): BottomSheetDialog(context) {
    private val binding by lazy { DialogSearchChoiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        initViews()
    }

    private fun initViews(){
        binding.dialogProductButton.setOnClickListener{
            onSearchProduct()
            dismiss()
        }

        binding.dialogIngredientButton.setOnClickListener{
            onSearchIngredient()
            dismiss()
        }

        binding.dialogKorProductButton.setOnClickListener {
            onSearchKorProduct()
            dismiss()
        }
    }
}