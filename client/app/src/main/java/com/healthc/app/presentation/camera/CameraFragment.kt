package com.healthc.app.presentation.camera

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.media.MediaActionSound
import android.media.MediaActionSound.SHUTTER_CLICK
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.healthc.app.databinding.FragmentCameraBinding
import com.healthc.app.presentation.detection.text_detection.model.OcrLanguage
import com.healthc.app.presentation.camera.contract.PickSinglePhotoContract
import com.healthc.app.utils.getCurrentFileName
import com.google.common.util.concurrent.ListenableFuture
import com.healthc.app.R
import kotlinx.coroutines.launch
import land.sungbin.systemuicontroller.setNavigationBarColor
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK // 후면 비율
    private var screenAspectRatio = AspectRatio.RATIO_4_3 // 3 : 4비율

    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
    private lateinit var imageCapture : ImageCapture
    private lateinit var imagePreview : Preview
    private lateinit var imageAnalyzer : ImageAnalysis

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraSound: MediaActionSound

    private lateinit var singlePhotoPickerLauncher : ActivityResultLauncher<Void?>

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateToHome()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        singlePhotoPickerLauncher =  registerForActivityResult(PickSinglePhotoContract()) { imageUri: Uri? ->
            if (imageUri != null) {
                navigateToDetection(imageUri.toString())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

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
        initView()
        setUpCamera()
        initButton()
    }

    private fun initCamera(){
        setNavigationBarColor(Color.BLACK) // navigation bar color black
        cameraExecutor = Executors.newSingleThreadExecutor() // init executors
        cameraSound = MediaActionSound() // init cameraSound
    }

    private fun initView(){
        binding.cameraChipGroup.check(binding.odCameraChip.id) // initial chip state

        binding.btCameraCancel.setOnClickListener {
            navigateToHome()
        }
    }

    private fun setUpCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireActivity())
        cameraProviderFuture.addListener(
            {
                bindCameraUseCases()
            },
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun initButton(){
        binding.captureImageButton.setOnClickListener{
            captureImage()
            binding.captureImageButton.isEnabled = false
        }

        binding.switchCameraButton.setOnClickListener{
            lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                CameraSelector.LENS_FACING_BACK
            } else {
                CameraSelector.LENS_FACING_FRONT
            }
            // 카메라 렌즈 바인딩 변경으로, UseCases 재설정
            bindCameraUseCases()
        }

        binding.goToGalleryButton.setOnClickListener{
            singlePhotoPickerLauncher.launch()
        }
    }

    private fun bindCameraUseCases(){
        val cameraProvider = cameraProviderFuture.get()
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

        binding.CameraPreview.scaleType = PreviewView.ScaleType.FILL_CENTER

        cameraProvider.bindToLifecycle(this, cameraSelector, imagePreview,
            imageAnalyzer, imageCapture) // 라이프 사이클 바인딩
    }

    private fun captureImage(){
        val outputFileOptions = ImageCapture.OutputFileOptions
            .Builder(requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, ContentValues().getCurrentFileName()
            ).build()

        imageCapture.takePicture(outputFileOptions, cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    navigateToDetection(outputFileResults.savedUri.toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    Timber.d(exception.message)
                }
            }
        )
        startCaptureSound()
    }

    private fun startCaptureSound(){
        cameraSound.play(SHUTTER_CLICK) // camera sound
    }

    private fun navigateToDetection(imageUrl : String) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                when (binding.cameraChipGroup.checkedChipId) {
                    binding.engOcrChip.id -> {
                        navigateToTextDetection(imageUrl, OcrLanguage.ENG)
                    }
                    binding.korOcrChip.id -> {
                        navigateToTextDetection(imageUrl, OcrLanguage.KOR)
                    }
                    binding.odCameraChip.id -> {
                        navigateToObjectDetection(imageUrl)
                    }
                }
            }
        }
    }

    private fun navigateToTextDetection(imageUrl: String, language: OcrLanguage){
        val direction = CameraFragmentDirections.actionCameraFragmentToTextDetectionFragment(
            imageUrl = imageUrl,
            language = language
        )
        findNavController().navigate(direction)
    }

    private fun navigateToObjectDetection(imageUrl: String){
        findNavController().navigate(
            CameraFragmentDirections.actionCameraFragmentToObjectDetectionFragment(
                imageUrl
            )
        )
    }

    private fun navigateToHome() {
        findNavController().navigate(
            CameraFragmentDirections.actionCameraFragmentToHomeFragment()
        )
    }

    override fun onDetach() {
        callback.remove()
        super.onDetach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        cameraExecutor.shutdown()
        cameraSound.release()
    }
}