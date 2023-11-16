package com.healthc.app.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.healthc.app.databinding.DialogObjectDetectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class ObjectDetectionDialog(
    context : Context,
    private val detectedObject : String,
    private val onClickPosButton : (String) -> Unit,
    private val onClickNegButton : () -> Unit,
): BottomSheetDialog(context) {

    private val binding by lazy { DialogObjectDetectionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(){
        binding.dialogCategoryTextView.text = "인식된 음식 : $detectedObject"

        binding.dialogObjectDetectNegButton.setOnClickListener {
            onClickNegButton()
            dismiss()
        }

        binding.dialogObjectDetectPosButton.setOnClickListener {
            onClickPosButton(detectedObject)
            dismiss()
        }
    }
}