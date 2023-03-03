package com.example.healthc.presentation.food.product.search_product

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthc.R
import com.example.healthc.databinding.FragmentSearchProductBinding
import com.example.healthc.presentation.food.product.search_product.SearchProductViewModel.SearchProductUiEvent
import com.example.healthc.presentation.food.product.search_product.adapter.SearchProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : SearchProductViewModel by viewModels()
    private val args : SearchProductFragmentArgs by navArgs()

    private lateinit var searchProductAdapter : SearchProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViewModelState()
        initAdapter()
        initButton()
    }

    private fun observeData(){
        viewModel.searchProductUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is SearchProductUiEvent.Unit -> {}

                    is SearchProductUiEvent.Success -> {
                        searchProductAdapter.submitList(it.foodIngredient.body.items)
                    }

                    is SearchProductUiEvent.Failure -> {
                        // TODO ERROR
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViewModelState(){
        viewModel.setProduct(args.category)
        viewModel.getFoodIngredient()
    }

    private fun initAdapter(){
        searchProductAdapter = SearchProductAdapter()
        binding.searchProductRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchProductRecyclerView.adapter = searchProductAdapter
    }

    private fun initButton(){
        binding.backToProductButton.setOnClickListener{
            navigateToProduct()
        }
    }

    private fun navigateToProduct(){
        lifecycleScope.launchWhenStarted {
            val direction = SearchProductFragmentDirections
                .actionSearchProductFragmentToProductFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}