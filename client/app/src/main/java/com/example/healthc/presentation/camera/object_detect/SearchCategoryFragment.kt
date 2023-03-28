package com.example.healthc.presentation.camera.object_detect

import android.net.Uri
import android.os.Bundle
import android.util.Base64
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
import com.example.healthc.databinding.FragmentSearchCategoryBinding
import com.example.healthc.domain.model.food.SearchFoodCategory
import com.example.healthc.presentation.camera.object_detect.SearchCategoryViewModel.UiEvent
import com.example.healthc.presentation.widget.NegativeSignDialog
import com.example.healthc.presentation.widget.PositiveSignDialog
import com.example.healthc.presentation.widget.SearchCategoryDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchCategoryFragment : Fragment() {

    private var _binding: FragmentSearchCategoryBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : SearchCategoryViewModel by viewModels()
    private val args : SearchCategoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_category, container, false)
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
            val encodedImage: String = Base64.encodeToString(bytes, Base64.DEFAULT)
            viewModel.searchCategory(encodedImage)
        }else{
            Toast.makeText(requireContext(), "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            navigateToCamera()
        }
    }

    private fun navigateToCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = SearchCategoryFragmentDirections.actionSearchCategoryFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    private fun observeData(){
        viewModel.searchCategoryUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiEvent.Unit -> {}

                    is UiEvent.Success -> { // 객체 인식 성공
                        showDialog(it.category)
                        makeInvisibleProgressBar()
                    }

                    is UiEvent.Failure -> { // 객체 인식 실패
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        makeInvisibleProgressBar()
                    }

                    is UiEvent.Detected -> { // 알러지 성분 검출
                        NegativeSignDialog(
                            context = requireContext(),
                            detectedList = it.detectedList
                        ).show()
                    }

                    is UiEvent.DetectNoting -> { // 알러지 성분 불검출
                        PositiveSignDialog(requireContext()).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun makeInvisibleProgressBar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showDialog(category: SearchFoodCategory){
        SearchCategoryDialog(
            context = requireContext(),
            category = category,
            onClickNegButton = {
                navigateToCamera()
            },
            onClickPosButton = {
                viewModel.searchIngredients(it)
            }
        ).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}