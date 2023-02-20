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
import androidx.navigation.fragment.navArgs
import com.example.healthc.R
import com.example.healthc.databinding.FragmentImageProcessBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions

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
        setImage()
        recognizeText()
    }

    private fun setImage(){
        viewModel.setImageUrl(args.imageUrl)
    }

    private fun recognizeText(){
        val image = InputImage.fromFilePath(requireContext(), args.imageUrl.toUri())
        val textRecognizer = TextRecognition.getClient(KoreanTextRecognizerOptions.Builder().build())
        textRecognizer.process(image)
            .addOnSuccessListener { text ->
                viewModel.setImageText(text.text.replace("\n", ""))
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "인식할 수 있는 글자가 없습니다.", Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(requireContext(), "인식할 수 있는 글자가 없습니다.", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}