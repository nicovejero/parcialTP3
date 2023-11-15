package com.kubernights.tp3.parcialnw.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.kubernights.tp3.parcialnw.R
import com.kubernights.tp3.parcialnw.data.database.entities.toDatabase
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.databinding.FragmentPetDetailBinding
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.domain.model.toDomain
import com.kubernights.tp3.parcialnw.domain.model.toModel
import com.kubernights.tp3.parcialnw.ui.viewmodel.PetDetailViewModel
import com.kubernights.tp3.parcialnw.ui.adapter.ImageAdapter

class PetDetailFragment : Fragment() {
    private val viewModel: PetDetailViewModel by viewModels()
    private val args: PetDetailFragmentArgs by navArgs()
    private val pet: Dog by lazy { args.pet }
    private lateinit var binding: FragmentPetDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getImageUrlsForPet(pet)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetDetailBinding.inflate(inflater, container, false)
        setupUI()
        // Observe LiveData for image URLs to update RecyclerView
        viewModel.imageUrls.observe(viewLifecycleOwner) { imageUrls ->
            imageUrls.toList().let {
                //setupRecyclerView(it as ArrayList<String>)
            }
        }

        // Observe ViewModel LiveData for operation status
        viewModel.operationStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                is PetDetailViewModel.OperationStatus.Success -> {
                    Toast.makeText(context, "Adoption successful!", Toast.LENGTH_SHORT).show()

                }

                is PetDetailViewModel.OperationStatus.Failure -> {
                    Toast.makeText(context, "Error: ${status.message}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        return binding.root
    }

    private fun setupUI() {
        // Setup UI elements with petModel data
        binding.petDetailName.text = pet.petName
        binding.petDetailWeight.text = pet.petWeight.toString()
        binding.petDetailGender.text = pet.petGender
        binding.petDetailAge.text = pet.petAge.toString()
        binding.petDetailOwnerName.text = pet.petName
        binding.petDetailLocation.text = pet.petLocation
        binding.PetDetailDescripcion.text = pet.description

        binding.petDetailCallButton.setOnClickListener {
            //initiateCall(petModel.contactNumber)
            initiateCall("123456789")
        }

        binding.adoptButton.setOnClickListener {
            viewModel.petAdoption(pet)
            val action = PetDetailFragmentDirections.actionGlobalToAdopcionFragment()
            findNavController().popBackStack(R.id.nav_graph, false)
            findNavController().navigate(action)
        } ?: Toast.makeText(requireContext(), "You must be logged in to adopt a pet", Toast.LENGTH_SHORT).show()
    }
        // Consider placing the following button click listeners here if not already defined
/*
    private fun setupRecyclerView(imageUrls: ArrayList<String>) {
        // Assuming ImageAdapter is the adapter for RecyclerView
        val adapter = ImageAdapter(requireContext(), imageUrls)
        val recyclerView: RecyclerView = binding.petDetailPicture
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : ImageAdapter.OnItemClickListener {
            override fun onClick(imageView: ImageView, path: String) {
                // The same code as before for click event
            }
        })
    }
*/
    private fun initiateCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }
}