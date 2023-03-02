package com.example.healthc.presentation.food.search_ingredient

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthc.R
import com.example.healthc.databinding.FragmentSearchIngredientBinding
import com.example.healthc.presentation.food.search_ingredient.SearchIngredientViewModel.SearchDictionaryUiEvent
import com.example.healthc.presentation.food.search_ingredient.adapter.SearchIngredientAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SearchIngredientFragment : Fragment() {

    private var _binding: FragmentSearchIngredientBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : SearchIngredientViewModel by viewModels()
    private val args : SearchIngredientFragmentArgs by navArgs()

    private lateinit var callback: OnBackPressedCallback
    private lateinit var searchIngredientAdapter: SearchIngredientAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_ingredient, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initViewModelState()
        initAdapter()
        initButton()
    }

    private fun observeData(){
        viewModel.searchDictionaryUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is SearchDictionaryUiEvent.Unit -> {}

                    is SearchDictionaryUiEvent.Success -> {
                        searchIngredientAdapter.submitList(it.foodIngredient.results)
                    }

                    is SearchDictionaryUiEvent.Failure -> {
                        // TODO ERROR
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initViewModelState(){
        viewModel.setAllergy(args.allergy)
        viewModel.getFoodIngredient()
    }

    private fun initAdapter(){
        searchIngredientAdapter = SearchIngredientAdapter()
        binding.searchIngredientList.layoutManager = LinearLayoutManager(requireContext())
        binding.searchIngredientList.adapter = searchIngredientAdapter
    }

    private fun initButton(){
        binding.backToFoodButton.setOnClickListener{
            navigateToFood()
        }
    }

    private fun navigateToFood(){
        lifecycleScope.launchWhenStarted {
            val direction = SearchIngredientFragmentDirections
                .actionSearchIngredientFragmentToIngredientFragment()
            findNavController().navigate(direction)
        }
    }

    private fun onBackPressButton(){
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToFood()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        callback.remove()
        _binding = null
    }
}