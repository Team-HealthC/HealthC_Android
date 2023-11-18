package com.healthc.app.presentation.recipe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.healthc.app.R
import com.healthc.app.databinding.FragmentRecipeSearchBinding

import com.google.android.material.chip.Chip

class RecipeSearchFragment : Fragment() {

    private var _binding: FragmentRecipeSearchBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackPressButton()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChips()
        initButton()
    }

    private fun initChips(){
        binding.foodAllergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            var checkedAllergy = DEFAULT_TEXT
            checkedIds.forEach{ id ->
                checkedAllergy = group.findViewById<Chip>(id).text.toString()
            }
            navigateToRecipe(checkedAllergy)
        }

        binding.searchEditTextView.setOnEditorActionListener { textView, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                navigateToRecipe(
                    textView.text.toString()
                )
                true
            }
            else false
        }
    }

    private fun initButton(){
        binding.backToCameraButton.setOnClickListener {
            navigateToCamera()
        }
    }

    private fun navigateToRecipe(allergy: String){
        findNavController().navigate(
            RecipeSearchFragmentDirections.actionRecipeSearchFragmentToRecipeFragment(allergy)
        )
    }

    private fun navigateToCamera(){
        val direction = RecipeSearchFragmentDirections.actionRecipeSearchFragmentToHomeFragment()
        findNavController().navigate(direction)
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

    companion object{
        const val DEFAULT_TEXT = ""
    }
}