package com.example.healthc.presentation.allergy_information.product.product_search

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProductSearchBinding
import com.example.healthc.presentation.allergy_information.product.product_search.adapter.ProductIdAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductSearchFragment : Fragment() {

    private var _binding: FragmentProductSearchBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ProductSearchViewModel by viewModels()
    private lateinit var productIdAdapter : ProductIdAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        initButtons()
    }

    private fun observeData(){
        viewModel.productIdEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                initViews() // View 상태 초기화

                when(it){
                    is ProductSearchViewModel.ProductIdUiEvent.Unit -> {}

                    is ProductSearchViewModel.ProductIdUiEvent.Loading -> {
                        showProgressbar()
                    }

                    is ProductSearchViewModel.ProductIdUiEvent.Success -> {
                        productIdAdapter.submitList(
                            it.productId.products
                        )
                    }
                    is ProductSearchViewModel.ProductIdUiEvent.Failure -> {
                        Toast.makeText(requireContext(), "상품 정보를 가져오는데 실패하였습니다.",
                            Toast.LENGTH_SHORT).show()
                    }

                    is ProductSearchViewModel.ProductIdUiEvent.NotFounded -> {
                        showNotFoundedText()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViews(){
        binding.progressBar.visibility = View.GONE
        binding.notFoundedProductTextView.visibility = View.GONE
        productIdAdapter.submitList(emptyList())
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

    private fun initButtons(){
        binding.backToCameraButton.setOnClickListener {
            navigateCamera()
        }
    }

    private fun showNotFoundedText(){
        binding.notFoundedProductTextView.visibility = View.VISIBLE
    }

    private fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun navigateProductInfo(id: Int){
        lifecycleScope.launchWhenStarted {
            val direction = ProductSearchFragmentDirections.actionProductSearchFragmentToProductFragment(
                productId = id
            )
            findNavController().navigate(direction)
        }
    }

    private fun navigateCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = ProductSearchFragmentDirections.actionProductSearchFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}