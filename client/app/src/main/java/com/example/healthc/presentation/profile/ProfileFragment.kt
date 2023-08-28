package com.example.healthc.presentation.profile

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
import com.example.healthc.R
import com.example.healthc.databinding.FragmentProfileBinding
import com.example.healthc.presentation.auth.AuthActivity
import com.example.healthc.presentation.profile.adapter.ProfileAllergyAdapter
import com.example.healthc.presentation.widget.NameEditDialog
import com.example.healthc.presentation.profile.ProfileViewModel.ProfileEvent
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

        binding.signOutButton.setOnClickListener{
            startAuthActivity() // 로그인 화면으로 전환
        }

        binding.backToCameraButton.setOnClickListener {
            navigateToCamera()
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

    private fun startAuthActivity() {
        val intent = Intent(requireContext(), AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun navigateToEditProfile(){
        val direction = ProfileFragmentDirections.actionProfileFragmentToEditorFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToCamera(){
        val direction = ProfileFragmentDirections.actionProfileFragmentToCameraFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}