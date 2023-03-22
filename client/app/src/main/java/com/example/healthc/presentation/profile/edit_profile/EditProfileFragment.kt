package com.example.healthc.presentation.profile.edit_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentEditProfileBinding
import com.example.healthc.presentation.profile.edit_profile.EditProfileViewModel.ProfileUiEvent
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class EditProfileFragment : Fragment(){

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : EditProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)
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
                     is ProfileUiEvent.Unit -> {}
                     is ProfileUiEvent.Success -> {
                         navigateToProfile() 
                     }
                     is ProfileUiEvent.Failure -> {
                         Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show() 
                     }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initView(){
        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val allergyList : MutableList<String> = mutableListOf()
            checkedIds.forEach{ id ->
                allergyList.add(group.findViewById<Chip>(id).text.toString())
            }
            viewModel.setAllergy(allergyList)
        }
    }

    private fun navigateToProfile(){
        lifecycleScope.launchWhenStarted {
            val direction = EditProfileFragmentDirections.actionEditProfileFragmentToProfileFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}