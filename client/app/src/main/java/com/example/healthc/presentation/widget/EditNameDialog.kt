package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogEditNameBinding
import com.example.healthc.domain.use_case.ValidateName

class EditNameDialog (
    context : Context,
    private val onDoneButtonClick: (String) -> Unit
): Dialog(context){

    private val binding by lazy { DialogEditNameBinding.inflate(layoutInflater) }
    private val validateName by lazy { ValidateName() }

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
        val validateResult = validateName(name)
        return if(!validateResult.successful){
            binding.dialogEditNameLayout.error = validateResult.errorMessage
            false
        }else{
            true
        }
    }

    private fun resizeDialog(){
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}