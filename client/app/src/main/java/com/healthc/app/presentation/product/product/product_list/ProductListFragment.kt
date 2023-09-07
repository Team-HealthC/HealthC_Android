package com.healthc.app.presentation.product.product.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthc.app.R
import com.healthc.app.databinding.FragmentProductListBinding
import com.healthc.app.presentation.product.product.product_list.ProductListViewModel.ProductListUiState
import com.healthc.app.presentation.product.product.product_list.adapter.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = requireNotNull(_binding){ "binding object is not initialized"}

    private val viewModel : ProductListViewModel by viewModels()
    private val args: ProductListFragmentArgs by navArgs()

    private lateinit var adapter : ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()
        initViewModelStates()
        observeData()
    }

    private fun initAdapter(){
        adapter = ProductListAdapter(
            onItemClick = { navigateToDetail(it)}
        )
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.productListRecyclerView.adapter = adapter
    }

    private fun initViews(){
        binding.backToSearchButton.setOnClickListener {
            navigateToSearch()
        }
    }

    private fun initViewModelStates(){
        viewModel.getProductIds(args.query)
    }

    private fun observeData(){
        viewModel.productListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ProductListUiState.Init -> { }

                    is ProductListUiState.NotFounded -> {

                    }
                    is ProductListUiState.Success -> {
                        adapter.submitList(it.productList)
                    }
                    is ProductListUiState.Failure -> {

                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToDetail(id: Int){
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductFragment(id)
        )
    }

    private fun navigateToSearch(){
        findNavController().navigate(
            ProductListFragmentDirections.actionProductListFragmentToProductSearchFragment()
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}