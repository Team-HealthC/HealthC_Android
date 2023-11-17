package com.healthc.app.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.healthc.app.databinding.DialogObjectDetectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.healthc.app.R
import com.healthc.data.model.local.detection.ObjectDetectionResult

class ObjectDetectionDialog(
    context : Context,
    private val objectDetectionResult : List<ObjectDetectionResult>,
    private val onClickPosButton : (String) -> Unit,
    private val onClickNegButton : () -> Unit,
): BottomSheetDialog(context) {

    private val binding by lazy { DialogObjectDetectionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        if(objectDetectionResult.isEmpty()) {
            initViewsIfFailedDetection()
        } else {
            initViewsIfSuccessfulDetection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewsIfSuccessfulDetection(){
        // binding.dialogCategoryTextView.text = "인식된 음식 : $detectedObject"

        binding.dialogObjectDetectNegButton.setOnClickListener {
            onClickNegButton()
            dismiss()
        }

        binding.dialogObjectDetectPosButton.setOnClickListener {
            // onClickPosButton(detectedObject)
            dismiss()
        }
    }

    private fun initViewsIfFailedDetection() {
        binding.dialogCategoryTextView.text =
            context.resources.getString(R.string.failed_object_detection_title)

        binding.dialogObjectDetectPosButton.isEnabled = false

        binding.dialogObjectDetectNegButton.setOnClickListener {
            onClickNegButton()
            dismiss()
        }
    }
}