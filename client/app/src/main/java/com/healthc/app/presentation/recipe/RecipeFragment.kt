package com.healthc.app.presentation.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.healthc.app.R
import com.healthc.app.databinding.FragmentRecipeBinding
import com.healthc.app.presentation.recipe.adapter.RecipeAdapter
import com.healthc.app.presentation.recipe.RecipeViewModel.RecipeEvent
import com.healthc.app.presentation.recipe.RecipeViewModel.RecipeUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel: RecipeViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()

    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false)
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
        viewModel.recipeEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is RecipeEvent.Failure -> {
                        hideProgressbar()
                        Toast.makeText(requireContext(),
                            "음식 정보를 불러오는데 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.recipeListUiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is RecipeUiState.Init -> { }

                    is RecipeUiState.NotFounded -> {
                        showNotFoundedSign()
                        hideProgressbar()
                    }

                    is RecipeUiState.Success -> {
                        recipeAdapter.submitList(it.list)
                        hideProgressbar()
                    }
                }

            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showNotFoundedSign(){
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
        recipeAdapter = RecipeAdapter()
        binding.searchIngredientList.layoutManager = LinearLayoutManager(requireContext())
        binding.searchIngredientList.adapter = recipeAdapter
    }

    private fun initButton(){
        binding.backToFoodButton.setOnClickListener{
            navigateToFood()
        }
    }

    private fun navigateToFood(){
        val direction = RecipeFragmentDirections.actionRecipeFragmentToRecipeSearchFragment()
        findNavController().navigate(direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}