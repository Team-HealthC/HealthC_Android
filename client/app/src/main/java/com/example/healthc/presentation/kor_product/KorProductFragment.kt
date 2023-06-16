package com.example.healthc.presentation.kor_product

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
import com.example.healthc.databinding.FragmentKorProductBinding
import com.example.healthc.presentation.kor_product.KorProductViewModel.SearchProductUiEvent
import com.example.healthc.presentation.kor_product.adapter.KorProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class KorProductFragment : Fragment() {

    private var _binding: FragmentKorProductBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : KorProductViewModel by viewModels()

    private lateinit var korProductAdapter : KorProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kor_product, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initAdapter()
        initButtons()
    }

    private fun observeData(){
        viewModel.searchProductUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                initViews() // View State 초기화
                when(it){
                    is SearchProductUiEvent.Unit -> {}

                    is SearchProductUiEvent.Loading -> {
                        showProgressbar()
                    }

                    is SearchProductUiEvent.Success -> {
                        korProductAdapter.submitList(it.foodIngredient)
                        hideProgressbar()
                    }

                    is SearchProductUiEvent.Failure -> {
                        Toast.makeText(requireContext(), "상품 정보를 가져오는데 실패하였습니다.",
                            Toast.LENGTH_SHORT).show()
                        hideProgressbar()
                    }

                    is SearchProductUiEvent.NotFounded -> {
                        showNotFoundedText()
                        hideProgressbar()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViews(){
        binding.progressBar.visibility = View.GONE
        binding.notFoundedKorProduct.visibility = View.GONE
        korProductAdapter.submitList(emptyList())
    }

    private fun initAdapter(){
        korProductAdapter = KorProductAdapter()
        binding.searchProductRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchProductRecyclerView.adapter = korProductAdapter
    }

    private fun initButtons(){
        binding.backToCameraButton.setOnClickListener{
            navigateToCamera()
        }
    }

    private fun showNotFoundedText(){
        binding.notFoundedKorProduct.visibility = View.VISIBLE
    }

    private fun hideProgressbar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressbar(){
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun navigateToCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = KorProductFragmentDirections.actionKorProductFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}