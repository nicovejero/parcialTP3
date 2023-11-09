package com.kubernights.tp3.parcialnw.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kubernights.tp3.parcialnw.databinding.FragmentHomeBinding
import com.kubernights.tp3.parcialnw.ui.adapter.FilterChipAdapter
import com.kubernights.tp3.parcialnw.ui.adapter.PetAdoptableAdapter
import com.kubernights.tp3.parcialnw.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var petAdapter: PetAdoptableAdapter
    private lateinit var filterAdapter: FilterChipAdapter
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.onCreate()
        setupRecyclerViews()
        setupObservers()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            binding.loading.isVisible = it
        })

    }
    private fun setupRecyclerViews() {
        petAdapter = PetAdoptableAdapter(mutableListOf())
        binding.cardsRecyclerView.adapter = petAdapter
        binding.cardsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        filterAdapter = FilterChipAdapter(emptyList()) { breed ->
            viewModel.searchDogsByBreed(breed)
        }

        binding.chipsRecyclerView.adapter = filterAdapter
        binding.chipsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupObservers() {

        viewModel.dogModel.observe(viewLifecycleOwner) { dogs ->
            // Log to check if this is being called and what the data looks like
            Log.d("HomeFragment", "Dogs data received: $dogs")
            petAdapter.updateData(dogs)
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

}
