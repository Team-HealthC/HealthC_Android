package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogSearchBinding

class SearchDialog(
    context : Context,
    private val onSearchProduct : () -> Unit,
    private val onSearchIngredient : () -> Unit
): Dialog(context) {
    private val binding by lazy { DialogSearchBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
        initViews()
    }

    private fun initViews(){
        binding.dialogProductButton.setOnClickListener{
            onSearchIngredient()
            dismiss()
        }
        binding.dialogIngredientButton.setOnClickListener{
            onSearchProduct()
            dismiss()
        }
    }

    private fun resizeDialog(){
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}