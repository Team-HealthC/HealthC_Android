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
import com.example.healthc.presentation.camera.object_detect.SearchCategoryViewModel.SearchCategoryUiEvent
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
        viewModel.setImageUrl(args.imageUrl)
        observeData()
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
                    is SearchCategoryUiEvent.Unit -> {}

                    is SearchCategoryUiEvent.Success -> {
                        showDialog(it.category)
                    }

                    is SearchCategoryUiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showDialog(category: SearchFoodCategory){
        SearchCategoryDialog(
            context = requireContext(),
            category = category
        ).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}