package com.example.healthc.presentation.widget

import android.content.Context
import android.os.Bundle
import com.example.healthc.R
import com.example.healthc.databinding.DialogCameraStateBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CameraStateDialog(
    context : Context,
    private val setCameraState : (String) -> Unit,
): BottomSheetDialog(context) {
    private val binding by lazy { DialogCameraStateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        initViews()
    }

    private fun initViews(){
        with(binding.dialogObjectDetectButton){
            this.setOnClickListener {
                setCameraState(this.text.toString())
                dismiss()
            }
        }
        with(binding.dialogImageProcessEngButton){
            this.setOnClickListener {
                setCameraState(this.text.toString())
                dismiss()
            }
        }
        with(binding.dialogImageProcessKorButton){
            this.setOnClickListener {
                setCameraState(this.text.toString())
                dismiss()
            }
        }
    }
}