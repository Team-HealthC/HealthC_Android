package com.healthc.app.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.healthc.app.databinding.DialogNegativeBinding
import com.healthc.domain.model.auth.Allergy

class NegativeSignDialog(
    context: Context,
    private val detectedList : List<Allergy>,
) : Dialog(context){

    private val binding by lazy { DialogNegativeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
        initViews()
    }

    private fun initViews(){
        val content = "해당 음식에는 ${detectedList.joinToString(", ")}이(가) 포함되어 있습니다."
        binding.negativeDialogContent.text = content

        binding.negativeDialogBackButton.setOnClickListener{
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