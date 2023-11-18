package com.healthc.app.presentation.profile

import android.content.Intent
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthc.app.R
import com.healthc.app.databinding.FragmentProfileBinding
import com.healthc.app.presentation.profile.adapter.ProfileAllergyAdapter
import com.healthc.app.presentation.widget.NameEditDialog
import com.healthc.app.presentation.profile.ProfileViewModel.ProfileEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel : ProfileViewModel by viewModels()

    private lateinit var profileAllergyAdapter: ProfileAllergyAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModelState()
        initAdapter()
        initButton()
        observeData()
    }

    private fun initViewModelState(){
        viewModel.getProfile()
    }

    private fun observeData(){
        viewModel.profileUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is ProfileEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.userUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { list ->
                profileAllergyAdapter.submitList(list.allergies)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initButton(){
        binding.goToEditProfile.setOnClickListener{
            showDialog()
        }

        binding.goToEditAllergy.setOnClickListener{
            navigateToEditProfile()
        }

        binding.backToCameraButton.setOnClickListener {
            navigateToHome()
        }

        binding.btProfileAccount.setOnClickListener {
            navigateToAccount()
        }
    }

    private fun initAdapter(){
        profileAllergyAdapter = ProfileAllergyAdapter(requireContext())

        with(binding){
            profileAllergyRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
            profileAllergyRecyclerView.adapter = profileAllergyAdapter
        }
    }

    private fun showDialog(){
        NameEditDialog(
            requireContext(),
            onDoneButtonClick = { name ->
                viewModel.editName(name)
            }
        ).show()
    }

    private fun navigateToEditProfile(){
        val direction = ProfileFragmentDirections.actionProfileFragmentToEditorFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToHome(){
        val direction = ProfileFragmentDirections.actionProfileFragmentToHomeFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToAccount() {
        val direction = ProfileFragmentDirections.actionProfileFragmentToAccountFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}