package com.kubernights.tp3.parcialnw.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.domain.usecase.AddDogUseCase
import com.kubernights.tp3.parcialnw.domain.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PublishViewModel @Inject constructor(
    private val addDogUseCase: AddDogUseCase,
    private val getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {
    private val _resetFields = MutableLiveData<Boolean?>().apply { value = false }
    val resetFields: LiveData<Boolean?> = _resetFields

    private val _breeds = MutableLiveData<List<BreedWithSubBreeds>>()
    val breeds: LiveData<List<BreedWithSubBreeds>> = _breeds

    // Function to load breeds
    fun loadBreeds() {
        viewModelScope.launch {
            // Handle any loading state if needed
            try {
                val breedsList = getBreedsUseCase()
                _breeds.value = breedsList
            } catch (e: Exception) {
                // Handle any errors if needed
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


    fun onFieldsResetComplete() {
        _resetFields.value = false
    }
}
