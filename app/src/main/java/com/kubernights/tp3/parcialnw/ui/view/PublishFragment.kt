package com.kubernights.tp3.parcialnw.ui.view

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.kubernights.tp3.parcialnw.R
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import com.kubernights.tp3.parcialnw.ui.viewmodel.PublishViewModel
import com.kubernights.tp3.parcialnw.databinding.FragmentPublishBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PublishFragment : Fragment() {
    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PublishViewModel by viewModels()
    private var selectedImageViewId: Int = 0
    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { handleImagePicked(it) }
    }

    companion object {
        fun newInstance() = PublishFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        setupImageButtons()
        setupConfirmAdoptionButton()
        return binding.root
    }

    private fun setupConfirmAdoptionButton() {
        binding.confirmAdoptionButton.setOnClickListener {
            attemptToGeneratePetInfo()
        }
    }

    private fun setupImageButtons() {
        listOf(
            binding.simpleImageButton1,
            binding.simpleImageButton2,
            binding.simpleImageButton3,
            binding.simpleImageButton4,
            binding.simpleImageButton5
        ).forEach { button ->
            button.setOnClickListener {
                selectedImageViewId = it.id
                imagePickerLauncher.launch("image/*")
            }
        }
    }

    private fun handleImagePicked(uri: Uri) {
        val imageView = when (selectedImageViewId) {
            binding.simpleImageButton1.id -> binding.simpleImageButton1
            binding.simpleImageButton2.id -> binding.simpleImageButton2
            binding.simpleImageButton3.id -> binding.simpleImageButton3
            binding.simpleImageButton4.id -> binding.simpleImageButton4
            binding.simpleImageButton5.id -> binding.simpleImageButton5
            else -> null
        }
        imageView?.let { Glide.with(this).load(uri).into(it) }
    }

    private fun resetFields() {
        with(binding) {
            eTNombrePet.text = null
            breedAutoComplete.setText("", false)
            subBreedAutoComplete.setText("", false)
            publicacionDescriptionInput.text = null
            ageSpinner.selectAll()
            locationsSpinner.clearListSelection()
            pesoDropdownContainer.text = null
            publicacionPhoneInput.text = null
            genderSwitch.isChecked = false
        }
        resetImages()
    }

    private fun resetImages() {
        // Implement the logic to reset images
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            //loadBreeds()
            setupObservers()
            initializeAdapters(requireContext(), binding.breedAutoComplete, binding.subBreedAutoComplete, binding.locationsSpinner, binding.ageSpinner)
            handleGenderSwitch(binding.genderSwitch, requireContext())
            setupButtonListeners(binding.confirmAdoptionButton, requireContext(), lifecycleScope)
        }
    }


    private fun setupObservers() {
        viewModel.resetFields.observe(viewLifecycleOwner) { shouldReset ->
            if (shouldReset == true) {
                resetFields()
                viewModel.onFieldsResetComplete()
            }
        }

        viewModel.breedsLiveData.observe(viewLifecycleOwner) { breedsWithSubBreeds ->
            updateBreedsList(breedsWithSubBreeds)
            setupBreedSelectionListener()
        }

        viewModel.subBreedsLiveData.observe(viewLifecycleOwner) { subBreedsList ->
            updateSubBreedsList(subBreedsList)
        }

    }

    private fun setupBreedSelectionListener() {
        binding.breedAutoComplete.setOnItemClickListener { adapterView, _, position, _ ->
            val selectedBreedName = adapterView.getItemAtPosition(position) as String
            // Now, find the BreedWithSubBreeds object from your data source using the selected breed name
            val selectedBreedWithSubBreeds = viewModel.breedsLiveData.value?.find { it.breed.breedName == selectedBreedName }

            // Once you have the BreedWithSubBreeds object, you can proceed to load the sub-breeds
            selectedBreedWithSubBreeds?.let {
                viewModel.loadSubBreedsForBreed(it.breed.id)
            }
        }
    }

    private fun updateBreedsList(breedsWithSubBreeds: List<BreedWithSubBreeds>) {
        // Extract the breed names from the list of BreedWithSubBreeds
        val breedNames = breedsWithSubBreeds.map { it.breed.breedName }

        // Update the AutoCompleteTextView with the list of breeds
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, breedNames)
        binding.breedAutoComplete.setAdapter(adapter)

        // Optional: If you want to handle sub-breeds as well, you can expand this function
        // to update another AutoCompleteTextView for sub-breeds based on the selected breed.
    }

    private fun updateSubBreedsList(subBreeds: List<SubBreedEntity>) {
        if (subBreeds.isNotEmpty()) {
            val subBreedsName = subBreeds.map { it.subBreedName }
            val subBreedAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_menu_popup_item, subBreedsName)
            binding.subBreedAutoComplete.apply {
                setAdapter(subBreedAdapter)
                visibility = View.VISIBLE
            }
        } else {
            // Hide the subBreedAutoComplete if there are no sub-breeds
            binding.subBreedAutoComplete.visibility = View.GONE
        }
    }

    private fun attemptToGeneratePetInfo() {
        val petName = binding.eTNombrePet.text.toString().trim()
        val petBreed = binding.breedAutoComplete.text.toString().trim()
        val petSubBreed = binding.subBreedAutoComplete.text.toString().trim()
        val petLocation = binding.locationsSpinner.text.toString().trim()
        val petAge = binding.ageSpinner.text.toString().trim() // Make sure to pass this as a string
        val petGender = if (binding.genderSwitch.isChecked) "Macho" else "Hembra"
        val petDescription = binding.publicacionDescriptionInput.text.toString()

        viewModel.createDog(
            petName,
            petBreed,
            petSubBreed,
            petLocation,
            petAge,
            petGender,
            petDescription
        ) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Publicacion creada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Publicacion Fallida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

