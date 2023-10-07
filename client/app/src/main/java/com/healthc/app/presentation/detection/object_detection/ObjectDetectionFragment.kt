package com.healthc.app.presentation.detection.object_detection

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.healthc.app.R
import com.healthc.app.databinding.FragmentObjectDetectionBinding
import com.healthc.domain.model.auth.Allergy
import com.healthc.domain.model.detection.ObjectDetection
import com.healthc.app.presentation.detection.object_detection.ObjectDetectionViewModel.ObjectDetectionEvent
import com.healthc.app.presentation.widget.NegativeSignDialog
import com.healthc.app.presentation.widget.ObjectDetectionDialog
import com.healthc.app.presentation.widget.PositiveSignDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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
        loadImage()
        observeData()
        initViews()
    }

    private fun initViews() {
        viewModel.setImageUrl(args.imageUrl)

        binding.backToCameraButton.setOnClickListener {
            navigateToCamera()
        }
    }

    private fun loadImage(){
        val inputStream = requireActivity().contentResolver.openInputStream(Uri.parse(args.imageUrl))
        if(inputStream != null) {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes)
            viewModel.postImage(bytes)
        }
        else{
            Toast.makeText(requireContext(), "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            navigateToCamera()
        }
        inputStream?.close()
    }

    private fun navigateToCamera(){
        val direction = ObjectDetectionFragmentDirections.actionObjectDetectionFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    private fun observeData(){
        viewModel.objectDetectionEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ObjectDetectionEvent.Success -> { // 객체 인식 성공
                        showObjectDetectionDialog(it.category)
                        eraseProgressBar()
                    }

                    is ObjectDetectionEvent.Failure -> { // 객체 인식 실패
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        eraseProgressBar()
                    }

                    is ObjectDetectionEvent.Detected -> { // 알러지 성분 검출
                        if(it.detectedList.isEmpty()){
                            showPositiveDialog()
                        }
                        else{
                            showNegativeDialog(it.detectedList)
                        }
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun eraseProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showObjectDetectionDialog(objectDetection: ObjectDetection){
        ObjectDetectionDialog(
            context = requireContext(),
            objectDetection = objectDetection,
            onClickNegButton = {
                navigateToCamera()
            },
            onClickPosButton = { detectedObject ->
                viewModel.checkAllergies(detectedObject = detectedObject)
            }
        ).show()
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}