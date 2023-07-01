package com.example.healthc.presentation.product.kor_product.product_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentKorProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KorProductFragment : Fragment() {

    private var _binding: FragmentKorProductBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_kor_product, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }

    private fun initButtons(){
        binding.backToSearchButton.setOnClickListener{
            navigateToList()
        }
    }

    private fun navigateToList(){
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}