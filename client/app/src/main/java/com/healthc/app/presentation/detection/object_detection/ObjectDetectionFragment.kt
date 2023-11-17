package com.healthc.app.presentation.detection.object_detection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthc.app.R
import com.healthc.app.databinding.FragmentObjectDetectionBinding
import com.healthc.app.presentation.detection.object_detection.ObjectDetectionViewModel.ObjectDetectionUiState
import com.healthc.app.presentation.detection.object_detection.ObjectDetectionViewModel.ObjectDetectionEvent
import com.healthc.app.presentation.detection.object_detection.adapter.ObjectDetectionAdapter
import com.healthc.app.presentation.widget.NegativeSignDialog
import com.healthc.app.presentation.widget.PositiveSignDialog
import com.healthc.data.model.local.detection.ObjectDetectionResult
import com.healthc.domain.model.auth.Allergy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class ObjectDetectionFragment : Fragment() {

    private var _binding: FragmentObjectDetectionBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel : ObjectDetectionViewModel by viewModels()
    private val args : ObjectDetectionFragmentArgs by navArgs()
    private lateinit var objectDetectionAdapter: ObjectDetectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_object_detection, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }

    private fun initViews() {
        viewModel.setImageUrl(args.imageUrl)

        binding.backToCameraButton.setOnClickListener {
            navigateToCamera()
        }

        binding.btODBackCamera.setOnClickListener {
            navigateToCamera()
        }

        // 이미지 크기 측정을 위해, 크기가 정해진 후 이미지 전처리 시작
        with(binding.ivODObjectImage) {
            viewTreeObserver.addOnGlobalLayoutListener(object: OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    loadPreprocessedImage()
                }
            })
        }
    }

    private fun observeData(){
        viewModel.objectDetectionEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ObjectDetectionEvent.Failure -> { // 객체 인식 실패
                        initViewsIfFailedDetection(it.error)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.detectedObjectUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ObjectDetectionUiState.Init -> {}

                    is ObjectDetectionUiState.Success -> { // 객체 인식 성공
                        if(it.objectDetectionResultList.isEmpty()) {
                            initViewsIfNotDetected()
                        } else {
                            initViewsIfDetected(it.objectDetectionResultList)
                        }
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.detectedAllergiesUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { detectedAllergies ->
                if(detectedAllergies.isEmpty()) {
                    showPositiveDialog()
                } else {
                    showNegativeDialog(detectedAllergies = detectedAllergies)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViewsIfFailedDetection(error: Throwable) {
        Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
        binding.cdODBottom.visibility = View.GONE
    }

    private fun initViewsIfNotDetected() {
        with(binding) {
            progressBar.visibility = View.GONE
            tvODResultTitle.text = resources.getString(R.string.failed_object_detection_title)
        }
    }

    private fun initViewsIfDetected(
        objectDetectionResultList: List<ObjectDetectionResult>
    ) {
        // Object Detection Rectangle Draw
        val classes = getLabelClasses()
        with(binding.objectDetectionResultView) {
            visibility = View.VISIBLE
            setObjectDetectionResult(objectDetectionResultList, classes)
            invalidate()
        }
        binding.progressBar.visibility = View.GONE

        // 검출된 음식 리스트 초기화
        initAdapter(objectDetectionResultList, classes)
    }

    private fun initAdapter(
        objectDetectionResultList: List<ObjectDetectionResult>,
        classes: List<String>
    ) {
        objectDetectionAdapter = ObjectDetectionAdapter(
            classes = classes,
            onClick = { detectedObject ->
                viewModel.checkAllergies(detectedObject)
            }
        )
        objectDetectionAdapter.submitList(objectDetectionResultList)
        with(binding.rvODDetectedObject) {
            this.layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.HORIZONTAL, false
            )
            this.adapter = objectDetectionAdapter
        }
    }

    private fun loadPreprocessedImage(){
        val imageUri = Uri.parse(args.imageUrl)
        val bufferedInputStream = BufferedInputStream(
            requireContext()
                .contentResolver
                .openInputStream(imageUri)
        )

        bufferedInputStream.mark(bufferedInputStream.available())

        BitmapFactory.Options().run {
            inJustDecodeBounds = false

            val bitmap = BitmapFactory.decodeStream(
                bufferedInputStream, null, this // padding null, this option
            )

            if(bitmap == null) {
                Toast.makeText(
                    requireActivity(), "이미지를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT
                ).show()
            }  else {
                preprocessImage(bitmap, imageUri)
            }
        }

        bufferedInputStream.close()
    }

    private fun preprocessImage(bitmap: Bitmap, uri: Uri) {
        val inputStream = requireContext()
            .contentResolver
            .openInputStream(uri) ?: return

        // 회전 정보 확인
        val exif = ExifInterface(inputStream)
        val orientation =  exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )
        inputStream.close()

        // 회전한 각도에 따라 이미지 회전
        val rotatedBitmap : Bitmap = when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }

        // 640*640에 맞게 이미지 리사이징
        val preprocessedBitmap = resizeBitmap(rotatedBitmap)

        viewModel.executeObjectDetection(
            bitmap = preprocessedBitmap,
            inputImageWidth = rotatedBitmap.width.toFloat(), // 리사이징 전 사진의 너비
            inputImageHeight = rotatedBitmap.height.toFloat(), // 리사이징 전 사진의 높이
            imageViewWidth = binding.ivODObjectImage.width.toFloat(),
            imageViewHeight = binding.ivODObjectImage.height.toFloat(),
        )
    }

    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
    }

    private fun resizeBitmap(bitmap: Bitmap): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, IMAGE_DEFAULT_WIDTH, IMAGE_DEFAULT_HEIGHT, true)
    }

    // assets 으로부터 라벨 가져오기
    private fun getLabelClasses(): List<String> {
        val br = BufferedReader(
            InputStreamReader(requireContext().assets.open("labels_ko.txt"))
        )
        var line: String?
        val classes: MutableList<String> = mutableListOf()

        while ((br.readLine().also { line = it }) != null) {
            line?.let { classes.add(it) }
        }

        return classes
    }

    private fun showNegativeDialog(detectedAllergies: List<Allergy>){
        NegativeSignDialog(
            context = requireContext(),
            detectedAllergies = detectedAllergies
        ).show()
    }

    private fun showPositiveDialog(){
        PositiveSignDialog(context = requireContext()).show()
    }

    private fun navigateToCamera(){
        val direction = ObjectDetectionFragmentDirections.actionObjectDetectionFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMAGE_DEFAULT_HEIGHT = 640
        const val IMAGE_DEFAULT_WIDTH = 640
    }
}