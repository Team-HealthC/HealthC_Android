package com.example.healthc.presentation.camera

import android.content.ContentValues
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentCameraBinding
import com.example.healthc.presentation.utils.getCurrentFileName
import com.google.common.util.concurrent.ListenableFuture
import timber.log.Timber

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture : ImageCapture
    private lateinit var imagePreview : Preview
    private lateinit var imageAnalyzer : ImageAnalysis

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCamera()
        initButton()
    }

    private fun initCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener(
            {
                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun initButton(){
        binding.CaptureImageButton.setOnClickListener{
            captureImage()
        }

        binding.SwitchCameraButton.setOnClickListener{
            lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            // 카메라 렌즈 바인딩 변경으로, UseCases 재설정
            bindCameraUseCases()
        }

        binding.goToProfileButton.setOnClickListener{
            navigateToProfile()
        }
    }

    private fun bindCameraUseCases(){
        val cameraProvider = cameraProviderFuture.get()
        val screenAspectRatio = AspectRatio.RATIO_16_9 // 16 : 9 비율
        val cameraSelector : CameraSelector = CameraSelector.Builder() // 카메라 옵션
            .requireLensFacing(lensFacing) // 후면 카메라
            .build()

        imagePreview = Preview.Builder() // preview 객체 생성
            .setTargetAspectRatio(screenAspectRatio) // 비율 설정
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY) // 효율 최대치
            .setTargetAspectRatio(screenAspectRatio)
            .build()

        cameraProvider.unbindAll() // for rebinding

        imagePreview.setSurfaceProvider(binding.CameraPreview.surfaceProvider) // view 와 객체 결합

        cameraProvider.bindToLifecycle(this, cameraSelector, imagePreview,
            imageAnalyzer, imageCapture) // 라이프 사이클 바인딩
    }

    private fun captureImage(){
        val outputFileOptions = ImageCapture.OutputFileOptions
            .Builder(requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues().getCurrentFileName()
            ).build()

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    navigateToCamera(outputFileResults.savedUri.toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    Timber.d(exception.message)
                }
            }
        )
    }

    private fun navigateToCamera(imageUrl : String) {
        lifecycleScope.launchWhenStarted {
            val direction = CameraFragmentDirections.actionCameraFragmentToShowImageFragment(
                imageUrl = imageUrl
            )
            findNavController().navigate(direction)
        }
    }

    private fun navigateToProfile() {
        lifecycleScope.launchWhenStarted {
            val direction = CameraFragmentDirections.actionCameraFragmentToProfileFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}