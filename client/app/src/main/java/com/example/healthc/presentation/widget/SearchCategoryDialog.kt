package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogSearchCategoryBinding
import com.example.healthc.domain.model.food.SearchFoodCategory

class SearchCategoryDialog(
    context : Context,
    private val category : SearchFoodCategory
): Dialog(context) {
    private val binding by lazy { DialogSearchCategoryBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
        initViews()
    }

    private fun initViews(){
        binding.dialogCategoryTextView.text = category.category
        binding.dialogProbabilityTextView.text = category.probability.toString()
    }

    private fun resizeDialog(){
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}