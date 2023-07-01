package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogSearchChoiceBinding

class SearchChoiceDialog(
    context : Context,
    private val onSearchProduct : () -> Unit,
    private val onSearchIngredient : () -> Unit,
): Dialog(context) {
    private val binding by lazy { DialogSearchChoiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
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

        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }
    private fun resizeDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}