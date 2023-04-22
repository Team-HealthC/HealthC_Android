package com.example.healthc.presentation.food.ingredient.search_ingredient

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.healthc.presentation.food.Dish.search_Dish.adapter.SearchDishAdapter
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
    private lateinit var searchIngredientAdapter: SearchDishAdapter

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
        viewModel.searchDishUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is SearchIngredientViewModel.SearchDishUiEvent.Unit -> {

                    }

                    is SearchIngredientViewModel.SearchDishUiEvent.Success -> {
                        hideProgressbar()
                        searchIngredientAdapter.submitList(it.dish)
                    }

                    is SearchIngredientViewModel.SearchDishUiEvent.NotFounded -> {
                        hideProgressbar()
                        showNotFoundedTextView()
                    }

                    is SearchIngredientViewModel.SearchDishUiEvent.Failure -> {
                        hideProgressbar()
                        Toast.makeText(requireContext(),
                            "음식 정보를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showNotFoundedTextView(){
        with(binding.notFoundedIngredientTextView){
            this.visibility = View.VISIBLE
        }
    }

    private fun hideProgressbar(){
        binding.progressBar.visibility = View.GONE
    }

    private fun initViewModelState(){
        viewModel.setAllergy(args.allergy)
        viewModel.getFoodIngredient()
    }

    private fun initAdapter(){
        searchIngredientAdapter = SearchDishAdapter()
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