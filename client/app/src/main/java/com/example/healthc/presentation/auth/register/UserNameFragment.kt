package com.example.healthc.presentation.auth.register

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
import com.example.healthc.R
import com.example.healthc.databinding.FragmentUserNameBinding
import com.example.healthc.presentation.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class UserNameFragment : Fragment() {

    private var _binding: FragmentUserNameBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_name, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initButton()
    }

    private fun initButton(){
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData(){
        viewModel.signUpUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when (it) {
                    is AuthViewModel.SignUpUiEvent.Success -> {
                        navigateToEmail()
                        viewModel.initState()
                    }
                    is AuthViewModel.SignUpUiEvent.Failure -> {
                        binding.signUpNameLayout.error = it.message
                    }
                    is AuthViewModel.SignUpUiEvent.Unit -> {}
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun navigateToEmail() {
        val direction = UserNameFragmentDirections.actionUserNameFragmentToUserEmailFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}