package com.kubernights.tp3.parcialnw.ui.viewmodel

import android.R
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.switchmaterial.SwitchMaterial
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.domain.usecase.AddDogUseCase
import com.kubernights.tp3.parcialnw.domain.usecase.GetBreedsUseCase
import com.kubernights.tp3.parcialnw.domain.usecase.GetSubBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(
    private val addDogUseCase: AddDogUseCase,
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getSubBreedsUseCase: GetSubBreedsUseCase
) : ViewModel() {
    private val _resetFields = MutableLiveData<Boolean>().apply { value = false }
    val resetFields: LiveData<Boolean> = _resetFields

    private val _breedsLiveData = MutableLiveData<List<BreedWithSubBreeds>>()
    val breedsLiveData: LiveData<List<BreedWithSubBreeds>> = _breedsLiveData

    private val _subBreedsLiveData = MutableLiveData<List<SubBreedEntity>>()
    val subBreedsLiveData: LiveData<List<SubBreedEntity>> = _subBreedsLiveData

    init {
        loadBreeds()
    }

    fun generatePetInfo(
        petName: String,
        petBreed: String,
        petSubBreed: String,
        petLocation: String,
        petAge: String,
        petGender: Boolean,
        petDescription: String
    ): DogModel? {

        val urlImages = listOf(
            "https://www.insidedogsworld.com/wp-content/uploads/2016/03/Dog-Pictures.jpg",
            "https://inspirationseek.com/wp-content/uploads/2016/02/Cute-Dog-Images.jpg"
        )
        val parsedPetAge = petAge.toIntOrNull() ?: return null
        val petWeight = 0.0 // Or retrieve from input if necessary

        // Validate the input data and return null if any of the required fields are missing
        if (petName.isEmpty() || petBreed.isEmpty()) return null

        return DogModel(
            petId = "0",
            petName = petName,
            petBreed = petBreed,
            petSubBreed = petSubBreed,
            urlImage = urlImages,
            petAge = parsedPetAge,
            petWeight = petWeight,
            petGender = petGender,
            petOwner = "0",
            petLocation = petLocation,
            petAdopted = false,
            creationTimestamp = System.currentTimeMillis(),
            petDescripcion = petDescription
        )
    }

    // Inside PublishViewModel
    fun initializeAdapters(context: Context, breedAutoComplete: AutoCompleteTextView, subBreedAutoComplete: AutoCompleteTextView, locationsSpinner: AutoCompleteTextView, ageSpinner: AutoCompleteTextView) {
        // Initialize your adapters here and set them to the views.
        // For example, you might have something like this:
        val ages = (1..20).toList() // Replace with your age range
        val locations = listOf("CABA", "GBA", "Cordoba", "Rosario", "Mendoza", "Salta", "Tucuman", "Neuquen", "Mar del Plata", "La Plata", "Santa Fe", "San Juan", "San Luis", "Corrientes", "Misiones", "Chaco", "Formosa", "Jujuy", "La Rioja", "Santiago del Estero", "Catamarca", "Chubut", "Tierra del Fuego", "Santa Cruz").toList()
        val ageAdapter = ArrayAdapter(context, R.layout.simple_dropdown_item_1line, ages.map { it.toString() })
        val locationsAdapter = ArrayAdapter(context, R.layout.simple_dropdown_item_1line, locations.map { it.toString() })

        val breedsAdapter = ArrayAdapter(context, R.layout.simple_dropdown_item_1line,
            ages) // Replace with actual data

        val subBreedsAdapter = ArrayAdapter(context, R.layout.simple_dropdown_item_1line,
            listOf("SubBreed A", "SubBreed B")) // Replace with actual data

        breedAutoComplete.setAdapter(breedsAdapter)
        subBreedAutoComplete.setAdapter(subBreedsAdapter)
        locationsSpinner.setAdapter(locationsAdapter)
        ageSpinner.setAdapter(ageAdapter)
    }

    fun handleGenderSwitch(genderSwitch: SwitchMaterial, context: Context) {
        genderSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Handle switch change
            val genderText = if (isChecked) "Male" else "Female"
            Toast.makeText(context, "Selected gender: $genderText", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupButtonListeners(confirmAdoptionButton: Button, context: Context, lifecycleScope: LifecycleCoroutineScope) {
        confirmAdoptionButton.setOnClickListener {
            // Implement the logic that happens when the confirm button is clicked.
            // For example, validate inputs and start network call to post data
            Toast.makeText(context, "Confirm button clicked", Toast.LENGTH_SHORT).show()

            // If you need to perform a long-running operation, use the lifecycleScope to launch a coroutine
            lifecycleScope.launch {

            }
        }
    }


    private fun loadBreeds() {
        viewModelScope.launch {
            // Handle any loading states or errors as needed
            try {
                val breedsWithSubBreeds = getBreedsUseCase()
                _breedsLiveData.value = breedsWithSubBreeds
            } catch (e: Exception) {
                // Handle the exception, possibly by setting an error message LiveData
            }
        }
    }

    fun loadSubBreedsForBreed(breedId: Long) {
        viewModelScope.launch {
            try {
                val subBreeds = getSubBreedsUseCase(breedId) // You would have to implement this use case
                _subBreedsLiveData.value = subBreeds
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }

    fun addDog(dog: Dog) {
        viewModelScope.launch {
            try {
                addDogUseCase(dog)
                // Handle the success scenario, maybe update UI
            } catch (e: Exception) {
                // Handle the error scenario
            }
        }
    }

    fun createDog(
        petName: String,
        petBreed: String,
        petSubBreed: String,
        petLocation: String,
        petAge: String,
        petGender: Boolean,
        petDescription: String,
        onResult: (Boolean) -> Unit
    ) {
        val dogModel = generatePetInfo(petName, petBreed, petSubBreed, petLocation, petAge, petGender, petDescription)
        if (dogModel != null) {
            val dog = Dog(
                id = dogModel.petId,
                ownerId = dogModel.petOwner,
                petName = dogModel.petName,
                petBreed = dogModel.petBreed,
                petSubBreed = dogModel.petSubBreed,
                petLocation = dogModel.petLocation,
                petAge = dogModel.petAge,
                petGender = dogModel.petGender,
                petIsAdopted = dogModel.petAdopted,
                imageUrls = dogModel.urlImage,
                creationDate = dogModel.creationTimestamp,
                description = dogModel.petDescripcion
            )
            viewModelScope.launch {
                try {
                    addDogUseCase(dog)
                    onResult(true)
                } catch (e: Exception) {
                    onResult(false)
                }
            }
        } else {
            onResult(false)
        }
    }

    fun onFieldsResetComplete() {
        _resetFields.value = false
    }
}
