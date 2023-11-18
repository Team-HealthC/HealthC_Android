package com.healthc.app.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.healthc.app.R
import com.healthc.app.databinding.FragmentRegisterInformationBinding
import com.healthc.app.presentation.auth.AuthViewModel
import com.healthc.app.presentation.auth.AuthViewModel.ValidateEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterInformationFragment : Fragment() {

    private var _binding: FragmentRegisterInformationBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_information, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initButton()
        initData()
    }

    private fun initButton(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData(){
        viewModel.validationEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is ValidateEvent.Success -> {
                        navigateToEmail()
                    }
                    is ValidateEvent.InvalidName -> {
                        binding.signUpNameLayout.error = it.message
                    }
                    is ValidateEvent.InvalidEmail -> {
                        binding.signUpEmailLayout.error = it.message
                    }
                    is ValidateEvent.InvalidPassword -> {
                        binding.signUpPasswordEditLayout.error = it.message
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initData(){
        viewModel.clearUserInput()
    }

    private fun navigateToEmail() {
        val direction = RegisterInformationFragmentDirections
            .actionRegisterInformationFragmentToRegisterAllergyFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}