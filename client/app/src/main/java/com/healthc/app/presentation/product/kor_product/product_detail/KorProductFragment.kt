package com.healthc.app.presentation.product.kor_product.product_detail

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
import com.healthc.app.R
import com.healthc.app.databinding.FragmentKorProductBinding
import com.healthc.app.presentation.product.kor_product.product_detail.KorProductViewModel.KorProductEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class KorProductFragment : Fragment() {

    private var _binding: FragmentKorProductBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: KorProductViewModel by viewModels()
    private val args: KorProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kor_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        initData()
        observeData()
    }

    private fun initButtons(){
        binding.backKorProductButton.setOnClickListener{
            navigateToList()
        }
    }

    private fun initData(){
        viewModel.getKorProduct(args.id)
    }

    private fun observeData(){
        viewModel.korProductEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is KorProductEvent.Failure -> {
                        Toast.makeText(requireContext(), "데이터를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }

                    is KorProductEvent.NotFounded -> {

                    }
                }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToList(){
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}