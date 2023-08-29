package com.example.healthc.presentation.detection.text_detection

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
import com.example.healthc.R
import com.example.healthc.databinding.FragmentTextDetectionBinding
import com.example.healthc.domain.model.auth.Allergy
import com.example.healthc.presentation.detection.text_detection.TextDetectionViewModel.TextDetectionEvent
import com.example.healthc.presentation.detection.text_detection.model.OcrLanguage
import com.example.healthc.presentation.widget.NegativeSignDialog
import com.example.healthc.presentation.widget.PositiveSignDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TextDetectionFragment : Fragment() {

    private var _binding: FragmentTextDetectionBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel : TextDetectionViewModel by viewModels()
    private val args : TextDetectionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_text_detection, container, false)
        binding.viewModel = viewModel // xml viewModel init
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initData()
        observeData()
    }

    private fun initViews(){
        binding.backToCameraButton.setOnClickListener{
            navigateToCamera()
        }
    }

    private fun initData(){
        viewModel.setImageUrl(args.imageUrl) // set ImageUri

        when(args.language){
            OcrLanguage.KOR -> {
                viewModel.checkAllergiesInKoreanText()
            }

            OcrLanguage.ENG -> {
                viewModel.checkAllergiesInEnglishText()
            }
        }
    }

    private fun observeData(){
        viewModel.textDetectionUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is TextDetectionEvent.Detected -> {
                        showNegativeDialog(it.detectedList)
                    }

                    is TextDetectionEvent.NotDetected -> {
                        showPositiveDialog()
                    }

                    is TextDetectionEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showNegativeDialog(detectedList: List<Allergy>){
        NegativeSignDialog(
            context = requireContext(),
            detectedList = detectedList
        ).show()
    }

    private fun showPositiveDialog(){
        PositiveSignDialog(context = requireContext()).show()
    }

    private fun navigateToCamera(){
        val direction = TextDetectionFragmentDirections.actionTextDetectionFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}