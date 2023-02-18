package com.example.healthc.presentation.camera.image_process

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.healthc.R
import com.example.healthc.databinding.FragmentShowImageBinding

class ShowImageFragment : Fragment() {

    private var _binding: FragmentShowImageBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ShowImageViewModel by viewModels()
    private val args : ShowImageFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_show_image, container, false)
        binding.viewModel = viewModel // xml viewModel init
        setImage()

        return binding.root
    }

    private fun setImage(){
        viewModel.setImageUrl(args.imageUrl)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}