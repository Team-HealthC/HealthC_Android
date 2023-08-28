package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogNameEditBinding
import com.example.healthc.domain.usecase.validation.ValidateNameUseCase

class NameEditDialog (
    context : Context,
    private val onDoneButtonClick: (String) -> Unit
): Dialog(context){

    private val binding by lazy { DialogNameEditBinding.inflate(layoutInflater) }
    private val validateNameUseCase by lazy { ValidateNameUseCase() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
        initViews()
    }

    private fun initViews(){
        binding.dialogEditDoneButton.setOnClickListener {
            if(validateName()){
                onDoneButtonClick(binding.dialogEditNameTextView.text.toString())
                dismiss()
            }
        }
    }

    private fun validateName(): Boolean{
        val name = binding.dialogEditNameTextView.text.toString()
        val validateResult = validateNameUseCase(name)
        if(!validateResult.successful){
            binding.dialogEditNameLayout.error = validateResult.errorMessage
            return false
        }
        else{
            return true
        }
    }

    private fun resizeDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}