package com.kubernights.tp3.parcialnw.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubernights.tp3.parcialnw.databinding.FragmentHomeBinding
import com.kubernights.tp3.parcialnw.ui.adapter.FilterChipAdapter
import com.kubernights.tp3.parcialnw.ui.adapter.PetAdoptableAdapter
import com.kubernights.tp3.parcialnw.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var petAdapter: PetAdoptableAdapter
    private lateinit var filterAdapter: FilterChipAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerViews()
        setupObservers()
        return binding.root
    }

    private fun setupRecyclerViews() {
        petAdapter = PetAdoptableAdapter(emptyList()) // Assuming now it takes a list
        binding.cardsRecyclerView.adapter = petAdapter
        binding.cardsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        filterAdapter = FilterChipAdapter(emptyList()) { breed ->
            viewModel.searchDogsByBreed(breed)
        }
        binding.chipsRecyclerView.adapter = filterAdapter
        binding.chipsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupObservers() {
        viewModel.dogs.observe(viewLifecycleOwner) { dogs ->
            petAdapter.updateData(dogs) // Assuming your adapter has an updateData method
        }

        // This assumes that breeds are still fetched from a separate source
        //viewModel.breeds.observe(viewLifecycleOwner) { breeds ->
        //    filterAdapter.updateChips(breeds.map { ChipModel(id = it.hashCode(), text = it) })
        //}

        // Error handling can be observed similarly if implemented in ViewModel
        // viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
        //     Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        // }
    }

    // Rest of the lifecycle methods like onStart and onStop are not needed if you're not using Firestore adapter anymore

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
