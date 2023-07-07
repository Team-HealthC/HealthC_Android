package com.example.healthc.presentation.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogPositiveBinding

class PositiveSignDialog(
    context: Context,
    private val userAllergies: List<String>
) : Dialog(context){

    private val binding by lazy { DialogPositiveBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(){
        binding.positiveDialogBackButton.setOnClickListener{
            dismiss()
        }
        binding.positiveDialogContent.text =
            "해당 음식에는 ${userAllergies.joinToString(", ")}이(가)\n 포함되어 있지 않습니다."
    }

    private fun resizeDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}