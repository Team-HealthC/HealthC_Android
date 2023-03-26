package com.example.healthc.presentation.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.example.healthc.databinding.DialogCameraStateBinding

class CameraStateDialog(
    context : Context,
    private val setCameraState : (String) -> Unit,
): Dialog(context) {
    private val binding by lazy { DialogCameraStateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(binding.root)
        resizeDialog()
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

    private fun resizeDialog(){
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}