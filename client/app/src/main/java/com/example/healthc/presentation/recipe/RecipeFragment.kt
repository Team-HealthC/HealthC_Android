package com.example.healthc.presentation.recipe

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
import com.example.healthc.databinding.FragmentRecipeBinding
import com.example.healthc.presentation.recipe.adapter.RecipeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val viewModel : RecipeViewModel by viewModels()
    private val args : RecipeFragmentArgs by navArgs()

    private lateinit var callback: OnBackPressedCallback
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressButton()
    }

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
        viewModel.searchDishUiEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is RecipeViewModel.SearchDishUiEvent.Unit -> {

                    }

                    is RecipeViewModel.SearchDishUiEvent.Success -> {
                        hideProgressbar()
                        recipeAdapter.submitList(it.dish)
                    }

                    is RecipeViewModel.SearchDishUiEvent.NotFounded -> {
                        hideProgressbar()
                        showNotFoundedTextView()
                    }

                    is RecipeViewModel.SearchDishUiEvent.Failure -> {
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
        lifecycleScope.launchWhenStarted {
            val direction = RecipeFragmentDirections
                .actionRecipeFragmentToRecipeSearchFragment()
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