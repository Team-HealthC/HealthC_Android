package com.healthc.app.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.healthc.app.R
import com.healthc.app.presentation.auth.AuthViewModel
import com.healthc.app.presentation.auth.AuthViewModel.AuthEvent
import com.healthc.app.presentation.main.MainActivity
import com.google.android.material.chip.Chip
import com.healthc.app.databinding.FragmentRegisterAllergyBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegisterAllergyFragment : Fragment() {

    private var _binding: FragmentRegisterAllergyBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register_allergy, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView(){
        binding.allergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val allergyList : MutableList<String> = mutableListOf()
            checkedIds.forEach{ id ->
                allergyList.add(group.findViewById<Chip>(id).text.toString())
            }
            viewModel.setAllergy(allergyList)
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeData(){
        viewModel.signUpEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is AuthEvent.Success -> {
                        startMainActivity()
                    }

                    is AuthEvent.Failure -> {
                        Toast.makeText(
                            requireContext(),
                            "회원가입에 실패하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun startMainActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}