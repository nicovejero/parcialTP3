package com.kubernights.tp3.parcialnw.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllDogsUseCase: GetAllDogsUseCase,
    private val getDogsByBreedUseCase: GetDogsByBreedUseCase,
//    private val getDogsByAdoptedStatusUseCase: GetDogsByAdoptedStatusUseCase,
    private val getDogsByLocationUseCase: GetDogsByLocationUseCase,
    private val getDogsByGenderUseCase: GetDogsByGenderUseCase,
    private val getDogsByAgeUseCase: GetDogsByAgeUseCase
    // Add other use cases if necessary
) : ViewModel() {

    private val _dogs = MutableLiveData<List<Dog>>()
    val dogs: LiveData<List<Dog>> = _dogs

    // Assuming there's a use case to get breeds, uncomment and inject if available
    // private val _breeds = MutableLiveData<List<String>>()
    // val breeds: LiveData<List<String>> = _breeds

    init {
        loadDogs(false)
        //loadBreeds()
    }

    fun loadDogs(isAdopted: Boolean) {
        viewModelScope.launch {
            val result = getAllDogsUseCase()
            // Assuming you only want to display not adopted dogs if isAdopted is false
            _dogs.value = if (isAdopted) result else result.filter { !it.petIsAdopted }
        }
    }

    fun searchDogsByBreed(breed: String) {
        viewModelScope.launch {
            val result = getDogsByBreedUseCase(breed)
            _dogs.value = result
        }
    }

    fun searchDogsByLocation(location: String) {
        viewModelScope.launch {
            val result = getDogsByLocationUseCase(location)
            _dogs.value = result
        }
    }

    fun searchDogsByAge(age: Int) {
        viewModelScope.launch {
            val result = getDogsByAgeUseCase(age)
            _dogs.value = result
        }
    }

    fun searchDogsByGender(gender: Boolean) {
        viewModelScope.launch {
            val result = getDogsByGenderUseCase(gender)
            _dogs.value = result
        }
    }

    // Uncomment and complete if you have a use case for loading breeds
    /*
    fun loadBreeds() {
        viewModelScope.launch {
            val breedList = getBreedsUseCase()
            _breeds.value = breedList
        }
    }
    */

    // Additional methods for filtering by age, gender, and location can be added here, calling the corresponding use cases
}
