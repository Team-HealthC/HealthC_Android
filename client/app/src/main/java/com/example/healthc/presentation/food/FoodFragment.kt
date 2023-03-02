package com.example.healthc.presentation.food

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.healthc.R
import com.example.healthc.databinding.FragmentFoodBinding
import com.example.healthc.presentation.utils.toEng
import com.google.android.material.chip.Chip

class FoodFragment : Fragment() {

    private var _binding: FragmentFoodBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_food, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChips()
    }

    private fun initChips(){
        binding.foodAllergyChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            var checkedAllergy = DEFAULT_TEXT
            checkedIds.forEach{ id ->
                checkedAllergy = group.findViewById<Chip>(id).text.toString()
            }
            navigateToSearchDictionary(checkedAllergy.toEng())
        }
        binding.searchFoodButton.setOnClickListener {
            navigateToSearchDictionary(
                binding.searchEditTextView.text.toString()
            )
        }
    }

    private fun navigateToSearchDictionary(allergy: String){
        lifecycleScope.launchWhenStarted {
            val direction = FoodFragmentDirections
                .actionIngredientFragmentToSearchIngredientFragment(allergy)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        const val DEFAULT_TEXT = "egg"
    }
}