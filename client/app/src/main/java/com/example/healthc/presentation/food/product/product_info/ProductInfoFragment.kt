package com.example.healthc.presentation.food.product.product_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProductInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductInfoFragment : Fragment() {

    private var _binding: FragmentProductInfoBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ProductInfoViewModel by viewModels()
    private val args : ProductInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_info, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeData()
        initViews()
    }

    private fun initViewModel(){
        viewModel.getProductInfo(args.productId)
        viewModel.getProductFact(args.productId)
    }

    private fun observeData(){
        viewModel.productInfoUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ProductInfoViewModel.UiEvent.Unit -> {}

                    is ProductInfoViewModel.UiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                    is ProductInfoViewModel.UiEvent.ImageFailure ->{
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.productImage.visibility = View.GONE
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViews(){
        binding.backToProductButton.setOnClickListener{
            navigateProduct()
        }
    }

    private fun navigateProduct(){
        lifecycleScope.launchWhenStarted {
            val direction = ProductInfoFragmentDirections.actionProductInfoFragmentToProductFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}