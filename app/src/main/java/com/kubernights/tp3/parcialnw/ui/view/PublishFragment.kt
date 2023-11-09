package com.kubernights.tp3.parcialnw.ui.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kubernights.tp3.parcialnw.ui.viewmodel.PublishViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.databinding.FragmentPublishBinding
import com.kubernights.tp3.parcialnw.domain.model.Dog
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PublishFragment : Fragment() {
    private var _binding: FragmentPublishBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PublishViewModel by viewModels()
    private var selectedImageViewId: Int = 0
    private val db = FirebaseFirestore.getInstance()

    companion object {
        fun newInstance() = PublishFragment()
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPublishBinding.inflate(inflater, container, false)
        setupImageButtons()
        return binding.root
    }

    private fun setupImageButtons() {
        listOf(
            binding.simpleImageButton1,
            binding.simpleImageButton2,
            binding.simpleImageButton3,
            binding.simpleImageButton4,
            binding.simpleImageButton5
        ).forEach { button ->
            button.setOnClickListener { startImagePicker(it.id) }
        }
    }

    private fun startImagePicker(imageViewId: Int) {
        selectedImageViewId = imageViewId
        Intent(Intent.ACTION_GET_CONTENT).also {
            it.type = "image/*"
            startActivityForResult(it, PICK_IMAGE_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                binding.run {
                    val imageView = when (selectedImageViewId) {
                        simpleImageButton1.id -> simpleImageButton1
                        simpleImageButton2.id -> simpleImageButton2
                        simpleImageButton3.id -> simpleImageButton3
                        simpleImageButton4.id -> simpleImageButton4
                        simpleImageButton5.id -> simpleImageButton5
                        else -> null
                    }
                    imageView?.let { Glide.with(this@PublishFragment).load(uri).into(it) }
                }
            }
        }
    }

    private fun guardarImagen(uri: Uri) {
        view?.findViewById<ImageView>(selectedImageViewId)?.let { imageView ->
            Glide.with(this).load(uri).circleCrop().into(imageView)
        }
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
            initializeAdapters()
            handleGenderSwitch()
            setupButtonListeners()
        }
    }

    private fun setupObservers() {
        viewModel.resetFields.observe(viewLifecycleOwner) { shouldReset ->
            if (shouldReset == true) {
                resetFields()
                viewModel.onFieldsResetComplete()
            }
        }

    // Observe breeds LiveData
        /*viewModel.breedsLiveData.observe(viewLifecycleOwner) { breedsList ->
            updateBreedsList(breedsList)
        }

        // Observe error LiveData
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                // Handle the error message
            }
        }

        // Observe resetFields LiveData


        // Observe subBreedsLiveData LiveData
        viewModel.subBreedsLiveData.observe(viewLifecycleOwner) { subBreedsList ->
            updateSubBreedsList(subBreedsList)
        }*/
    }

    private fun PublishViewModel.initializeAdapters() {
        val context = requireContext()
        binding.breedAutoComplete.setAdapter(
            ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line)
        )
        binding.subBreedAutoComplete.setAdapter(
            ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line)
        )
        val ages = (1..20).toList() // Replace with your age range
        val locations = listOf(
            "CABA",
            "GBA",
            "Cordoba",
            "Rosario",
            "Mendoza",
            "Salta",
            "Tucuman",
            "Neuquen",
            "Mar del Plata",
            "La Plata",
            "Santa Fe",
            "San Juan",
            "San Luis",
            "Entre Rios",
            "Corrientes",
            "Misiones",
            "Chaco",
            "Formosa",
            "Jujuy",
            "La Rioja",
            "Santiago del Estero",
            "Catamarca",
            "Chubut",
            "Tierra del Fuego",
            "Santa Cruz"
        ).toList()
        val ageAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            ages.map { it.toString() })
        val locationsAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            locations.map { it.toString() })
        binding.locationsSpinner.setAdapter(locationsAdapter)
        binding.ageSpinner.setAdapter(ageAdapter)
    }

    private fun PublishViewModel.handleGenderSwitch() {
        binding.genderSwitch.setOnCheckedChangeListener { _, isChecked ->
            val petGender = if (isChecked) "Macho" else "Hembra"
            Toast.makeText(requireContext(), "Gender: $petGender", Toast.LENGTH_SHORT).show()
        }
    }

    private fun PublishViewModel.setupButtonListeners() {
        binding.confirmAdoptionButton.setOnClickListener {
            generatePetInfo()?.let { petInfo -> val dog = Dog(
                    id = petInfo.petId,
                    ownerId = petInfo.petOwner,
                    petName = petInfo.petName,
                    petBreed = petInfo.petBreed,
                    petSubBreed = petInfo.petSubBreed,
                    petLocation = petInfo.petLocation,
                    petAge = petInfo.petAge,
                    petGender = petInfo.petGender,
                    petIsAdopted = petInfo.petAdopted,
                    imageUrls = petInfo.urlImage,
                    creationDate = petInfo.creationTimestamp,
                    description = petInfo.petDescripcion
                )
                Toast.makeText(context, "Publicacion creada", Toast.LENGTH_SHORT).show()

                lifecycleScope.launch {
                    viewModel.addDog(dog)
                }
            } ?: run {
                Toast.makeText(context, "Publicacion Fallida", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun generatePetInfo(): DogModel? {
        val petName = binding.eTNombrePet.text.toString().trim()
        val petBreed = binding.breedAutoComplete.text.toString().trim()
        val petSubBreed = binding.subBreedAutoComplete.text.toString().trim()
        val petLocation = binding.locationsSpinner.text.toString().trim()
        // Assuming you want to get URLs of images uploaded by the user, you would have a different mechanism to retrieve them
        val urlImages = listOf(
            "https://www.insidedogsworld.com/wp-content/uploads/2016/03/Dog-Pictures.jpg",
            "https://inspirationseek.com/wp-content/uploads/2016/02/Cute-Dog-Images.jpg"
        )
        val petAge = binding.ageSpinner.text.toString().toIntOrNull() ?: return null
        val petWeight = 0.0 //binding.pesoDropdownContainer.text.toString().toDoubleOrNull() ?: return null
        val petGender = binding.genderSwitch.isChecked
        val petDescripcion = binding.publicacionDescriptionInput.text.toString()
        // Validate the input data and return null if any of the required fields are missing
        //if (petName.isEmpty() || petBreed.isEmpty()) return null

        return DogModel(
            petId = "0",
            petName = petName,
            petBreed = petBreed,
            petSubBreed = petSubBreed,
            urlImage = urlImages,
            petAge = petAge,
            petWeight = petWeight,
            petGender = petGender,
            petOwner = "0",
            petLocation = petLocation,
            petAdopted = false,
            creationTimestamp = System.currentTimeMillis(),
            petDescripcion = petDescripcion
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
