package com.example.healthc.presentation.product.product_search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProductSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductSearchFragment : Fragment() {

    private var _binding: FragmentProductSearchBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initViews()
    }

    private fun initViews(){
        binding.productTypeChipGroup.check(binding.engProductChip.id)
    }

    private fun initButtons(){
        binding.backToCameraButton.setOnClickListener {
            navigateCamera()
        }

        binding.ProductSearchEditTextView.setOnEditorActionListener { textView, id, _ ->
            if(id == EditorInfo.IME_ACTION_SEARCH){
                if(binding.productTypeChipGroup.checkedChipId == binding.korProductChip.id){
                    navigateToKorProductList(textView.text.toString())
                }
                else{
                    navigateProductList(textView.text.toString())
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun navigateProductList(query: String){
        val direction = ProductSearchFragmentDirections.actionProductSearchFragmentToProductListFragment(
            query
        )
        findNavController().navigate(direction)
    }

    private fun navigateToKorProductList(query: String){
        val direction = ProductSearchFragmentDirections.actionProductSearchFragmentToKorProductListFragment(
            query
        )
        findNavController().navigate(direction)
    }

    private fun navigateCamera(){
        val direction = ProductSearchFragmentDirections.actionProductSearchFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}