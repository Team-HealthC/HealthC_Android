package com.example.healthc.presentation.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthc.R
import com.example.healthc.databinding.FragmentCameraBinding
import com.google.common.util.concurrent.ListenableFuture

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = checkNotNull(_binding)

    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        initCamera()
        return binding.root
    }

    private fun initCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener({
            val camera = cameraProviderFuture.get()
            bindPreview(camera)
        }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun bindPreview(cameraProvider : ProcessCameraProvider) {
        val preview : Preview = Preview.Builder() // preview 객체 생성
            .build()

        val cameraSelector : CameraSelector = CameraSelector.Builder() // 카메라 옵션
            .requireLensFacing(CameraSelector.LENS_FACING_BACK) // 후면 카메라
            .build()

        preview.setSurfaceProvider(binding.CameraPreview.surfaceProvider) // view 와 객체 결합
        cameraProvider.bindToLifecycle(this, cameraSelector, preview) // 라이프 사이클 바인딩
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}