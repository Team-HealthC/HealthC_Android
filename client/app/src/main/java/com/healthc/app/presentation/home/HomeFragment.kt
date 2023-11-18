package com.healthc.app.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.healthc.app.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.ibHomeProfile.setOnClickListener{
            navigateToProfile()
        }

        binding.btHomeProductSearch.setOnClickListener{
            navigateToProduct()
        }

        binding.cvHomeCameraContent.setOnClickListener {
            navigateToCamera()
        }

        binding.btHomeRecipe.setOnClickListener {
            navigateToRecipe()
        }
    }

    private fun navigateToProfile() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        )
    }

    private fun navigateToCamera() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToPermissionsFragment()
        )
    }

    private fun navigateToProduct() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToProductSearchFragment()
        )
    }

    private fun navigateToRecipe() {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToRecipeSearchFragment()
        )
    }
}