package com.example.healthc.presentation.food.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProductBinding
import com.example.healthc.presentation.food.product.adapter.ProductIdAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ProductViewModel by viewModels()
    private lateinit var productIdAdapter : ProductIdAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        initViews()
    }

    private fun observeData(){
        viewModel.productIdEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ProductViewModel.ProductIdUiEvent.Success -> {
                        productIdAdapter.submitList(
                            it.productId.products
                        )
                    }
                    is ProductViewModel.ProductIdUiEvent.Failure -> {

                    }
                    is ProductViewModel.ProductIdUiEvent.Unit -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    
    private fun initAdapter(){
        productIdAdapter = ProductIdAdapter(
            onItemClick = { id ->
                navigateProductInfo(id)
            }
        )
        binding.productIdRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productIdRecyclerView.adapter = productIdAdapter
    }

    private fun initViews(){
        binding.backToCameraButton.setOnClickListener {
            navigateCamera()
        }
    }

    private fun navigateProductInfo(id: Int){
        lifecycleScope.launchWhenStarted {
            val direction = ProductFragmentDirections.actionProductFragmentToProductInfoFragment(
                productId = id
            )
            findNavController().navigate(direction)
        }
    }

    private fun navigateCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = ProductFragmentDirections.actionProductFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}