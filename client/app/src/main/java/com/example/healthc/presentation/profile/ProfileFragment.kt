package com.example.healthc.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.healthc.presentation.profile.adapter.ProfileDiseaseAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val authViewModel : AuthViewModel by viewModels()
    private val viewModel : ProfileViewModel by viewModels()

    private lateinit var profileAllergyAdapter: ProfileAllergyAdapter
    private lateinit var profileDiseaseAdapter: ProfileDiseaseAdapter

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
        initAdapter()
        initButton()
        observeData()
    }

    private fun observeData(){
        viewModel.userInfo.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { userInfo ->
                profileAllergyAdapter.submitList(userInfo.allergy)
                profileDiseaseAdapter.submitList(userInfo.disease)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initButton(){
        binding.goToEditProfile.setOnClickListener{
            navigateToEditProfile()
        }

        binding.signOutButton.setOnClickListener{
            authViewModel.signOut() // 로그아웃
            startAuthActivity() // 로그인 화면으로 전환
        }
    }

    private fun initAdapter(){
        profileAllergyAdapter = ProfileAllergyAdapter()
        profileDiseaseAdapter = ProfileDiseaseAdapter()

        with(binding){
            profileAllergyRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
            profileAllergyRecyclerView.adapter = profileAllergyAdapter

            profileDiseaseRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false)
            profileDiseaseRecyclerView.adapter = profileDiseaseAdapter
        }
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}