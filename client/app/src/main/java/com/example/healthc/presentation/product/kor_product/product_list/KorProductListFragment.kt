package com.example.healthc.presentation.product.kor_product.product_list

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
import com.example.healthc.R
import com.example.healthc.databinding.FragmentKorProductListBinding
import com.example.healthc.presentation.product.kor_product.product_list.KorProductListViewModel.KorProductUiState
import com.example.healthc.presentation.product.kor_product.product_list.adapter.KorProductListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class KorProductListFragment : Fragment() {

    private var _binding: FragmentKorProductListBinding? = null
    private val binding get() = requireNotNull(_binding){ "binding object is not initialized"}

    private val viewModel : KorProductListViewModel by viewModels()
    private val args: KorProductListFragmentArgs by navArgs()

    private lateinit var adapter : KorProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kor_product_list, container, false)
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
        adapter = KorProductListAdapter(
            onItemClick = { id ->
                navigateToDetail(id)
            }
        )
        binding.korProductListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.korProductListRecyclerView.adapter = adapter
    }

    private fun initViews(){
        binding.backToSearchButton.setOnClickListener {
            navigateToSearch()
        }
    }

    private fun initViewModelStates(){
        viewModel.getKorProductList(args.query)
    }

    private fun observeData(){
        viewModel.korProductListState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is KorProductUiState.Init -> { }

                    is KorProductUiState.NotFounded -> {

                    }
                    is KorProductUiState.Success -> {
                        adapter.submitList(it.foodIngredient)
                    }
                    is KorProductUiState.Failure -> {

                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToSearch(){
        findNavController().navigate(
            KorProductListFragmentDirections.actionKorProductListFragmentToProductSearchFragment()
        )
    }

    private fun navigateToDetail(id: String){
        findNavController().navigate(
            KorProductListFragmentDirections.actionKorProductListFragmentToKorProductFragment(id)
        )
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}