package com.example.healthc.presentation.profile.profile_allergy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProfileAllergyBinding
import com.example.healthc.presentation.profile.profile_allergy.ProfileAllergyViewModel.ProfileUiEvent
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileAllergyFragment : Fragment(){

    private var _binding: FragmentProfileAllergyBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : ProfileAllergyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_allergy, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initView()
    }

    private fun observeData(){
        viewModel.profileUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                 when(it){
                     is ProfileUiEvent.Success -> {
                         navigateToProfile() 
                     }
                     is ProfileUiEvent.Failure -> {
                         Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show() 
                     }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.profileAllergy.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { list ->
                if(list.isNotEmpty()){
                    val chipGroup = binding.allergyChipGroup
                    chipGroup.forEach { chip ->
                        val text = chipGroup.findViewById<Chip>(chip.id).text.toString()
                        if(text in list){
                            chipGroup.check(chip.id)
                        }
                    }
                    binding.profileEditUserHaveText.text = list.joinToString(", ")
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initView(){
        viewModel.getProfile()

        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val allergyList : MutableList<String> = mutableListOf()
            checkedIds.forEach{ id ->
                allergyList.add(group.findViewById<Chip>(id).text.toString())
            }
            viewModel.setAllergy(allergyList.toList())
            binding.profileEditUserHaveText.text = allergyList.joinToString(", ")
        }

        binding.backToProfileButton.setOnClickListener {
            navigateToProfile()
        }
    }

    private fun navigateToProfile(){
        val direction = ProfileAllergyFragmentDirections.actionProfileAllergyFragmentToProfileFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}