package com.example.healthc.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.example.healthc.databinding.DialogObjectDetectionBinding
import com.example.healthc.domain.model.object_detection.DetectedObject
import com.google.android.material.bottomsheet.BottomSheetDialog

class ObjectDetectionDialog(
    context : Context,
    private val category : DetectedObject,
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
        binding.dialogCategoryTextView.text = "인식된 음식 : ${category.category}"

        binding.dialogObjectDetectNegButton.setOnClickListener {
            onClickNegButton()
            dismiss()
        }

        binding.dialogObjectDetectPosButton.setOnClickListener {
            onClickPosButton(category.category)
            dismiss()
        }
    }
}