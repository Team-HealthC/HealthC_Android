package com.healthc.app.presentation.detection.object_detection

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.healthc.app.R
import com.healthc.app.databinding.FragmentObjectDetectionBinding
import com.healthc.app.presentation.detection.object_detection.ObjectDetectionViewModel.ObjectDetectionEvent
import com.healthc.app.presentation.widget.NegativeSignDialog
import com.healthc.app.presentation.widget.ObjectDetectionDialog
import com.healthc.app.presentation.widget.PositiveSignDialog
import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.model.detection.ObjectDetection
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

@AndroidEntryPoint
class ObjectDetectionFragment : Fragment() {

    private var _binding: FragmentObjectDetectionBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel : ObjectDetectionViewModel by viewModels()
    private val args : ObjectDetectionFragmentArgs by navArgs()

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
        loadImage()
    }

    private fun initViews() {
        viewModel.setImageUrl(args.imageUrl)

        binding.backToCameraButton.setOnClickListener {
            navigateToCamera()
        }
    }

    private fun loadImage(){
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
        val resultBitmap = resizeBitmap(rotatedBitmap)

        viewModel.executeObjectDetection(
            resultBitmap,
            binding.CaptureImageView.width.toFloat(),
            binding.CaptureImageView.height.toFloat(),
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

    private fun observeData(){
        viewModel.objectDetectionEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ObjectDetectionEvent.Failure -> { // 객체 인식 실패
                        Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT).show()
                        eraseProgressBar()
                    }

                    /*is ObjectDetectionEvent.Detected -> { // 알러지 성분 검출
                        if(it.detectedList.isEmpty()){
                            showPositiveDialog()
                        }
                        else{
                            showNegativeDialog(it.detectedList)
                        }
                    }*/
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.detectedObjectUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ObjectDetectionViewModel.ObjectDetectionUiState.Success -> {
                        val classes = getLabelClasses()
                        val detectedObject = classes[it.objectDetectionResult.classIndex]
                        showObjectDetectionDialog(detectedObject)

                        eraseProgressBar()
                    }
                    is ObjectDetectionViewModel.ObjectDetectionUiState.Init -> {}
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showObjectDetectionDialog(detectedObject: String){
        ObjectDetectionDialog(
            context = requireContext(),
            detectedObject = detectedObject,
            onClickNegButton = {
                navigateToCamera()
            },
            onClickPosButton = { detectedObject ->
                viewModel.checkAllergies(detectedObject = detectedObject)
            }
        ).show()
    }

    // 에셋으로부터 라벨 가져오기
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

    private fun showNegativeDialog(detectedList: List<Allergy>){
        NegativeSignDialog(
            context = requireContext(),
            allergyList = detectedList
        ).show()
    }

    private fun showPositiveDialog(){
        PositiveSignDialog(context = requireContext()).show()
    }

    private fun eraseProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun navigateToCamera(){
        val direction = ObjectDetectionFragmentDirections.actionObjectDetectionFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val IMAGE_DEFAULT_HEIGHT = 640
        const val IMAGE_DEFAULT_WIDTH = 640
    }
}