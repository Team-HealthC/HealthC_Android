package com.healthc.app.presentation.widget

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.healthc.app.databinding.DialogPositiveBinding

class PositiveSignDialog(context: Context) : Dialog(context){
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
            "음식에 알레르기 요소가 포함되어 있지 않습니다."
    }

    private fun resizeDialog() {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}