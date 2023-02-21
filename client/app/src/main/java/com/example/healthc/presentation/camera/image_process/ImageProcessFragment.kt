package com.example.healthc.presentation.camera.image_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.healthc.R
import com.example.healthc.databinding.FragmentImageProcessBinding
import com.example.healthc.presentation.camera.image_process.ImageProcessViewModel.ImageProcessEvent
import com.example.healthc.presentation.widget.NegativeSignDialog
import com.example.healthc.presentation.widget.PositiveSignDialog
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ImageProcessFragment : Fragment() {

    private var _binding: FragmentImageProcessBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ImageProcessViewModel by viewModels()
    private val args : ImageProcessFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_process, container, false)
        binding.viewModel = viewModel // xml viewModel init
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recognizeText()
        initViews()
        observeData()
    }

    private fun initViews(){
        viewModel.setImageUrl(args.imageUrl) // set ImageUri

        binding.backToCameraButton.setOnClickListener{
            navigateToCamera()
        }
    }

    private fun recognizeText(){
        val image = InputImage.fromFilePath(requireContext(), args.imageUrl.toUri())
        val textRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
        textRecognizer.process(image)
            .addOnSuccessListener { text ->
                val recognizedText = text.text.replace("\n", "")
                viewModel.detectImage(recognizedText)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "인식할 수 있는 글자가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(requireContext(), "인식에 실패하였습니다. 다시 촬영 해주세요.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun observeData(){
        viewModel.imageProcessEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ImageProcessEvent.Unit -> {}

                    is ImageProcessEvent.Success -> {
                        PositiveSignDialog(requireContext()).show()
                    }

                    is ImageProcessEvent.Detected -> {
                        NegativeSignDialog(
                            context = requireContext(),
                            detectedList = it.detectedList
                        ).show()
                    }

                    is ImageProcessEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = ImageProcessFragmentDirections.actionImageProcessFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}