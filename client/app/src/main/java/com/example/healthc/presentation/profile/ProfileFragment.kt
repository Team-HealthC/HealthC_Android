package com.example.healthc.presentation.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
import com.example.healthc.presentation.auth.AuthViewModel
import com.example.healthc.presentation.profile.adapter.ProfileAllergyAdapter
import com.example.healthc.presentation.profile.ProfileViewModel.ProfileUiEvent
import com.example.healthc.presentation.widget.EditNameDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val authViewModel : AuthViewModel by viewModels()
    private val viewModel : ProfileViewModel by viewModels()

    private lateinit var callback: OnBackPressedCallback

    private lateinit var profileAllergyAdapter: ProfileAllergyAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressButton()
    }

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
                    is ProfileUiEvent.Unit -> {}

                    is ProfileUiEvent.Success -> {
                        profileAllergyAdapter.submitList(it.userInfo.allergy)
                    }

                    is ProfileUiEvent.Failure -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
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
            authViewModel.signOut() // 로그아웃
            startAuthActivity() // 로그인 화면으로 전환
        }
    }

    private fun initAdapter(){
        profileAllergyAdapter = ProfileAllergyAdapter()

        with(binding){
            profileAllergyRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
            profileAllergyRecyclerView.adapter = profileAllergyAdapter
        }
    }

    private fun showDialog(){
        EditNameDialog(
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
        lifecycleScope.launchWhenStarted {
            val direction = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment()
            findNavController().navigate(direction)
        }
    }

    private fun navigateToCamera(){
        lifecycleScope.launchWhenStarted {
            val direction = ProfileFragmentDirections.actionProfileFragmentToCameraFragment()
            findNavController().navigate(direction)
        }
    }

    private fun onBackPressButton(){
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToCamera()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        _binding = null
        callback.remove()
        super.onDestroyView()
    }
}