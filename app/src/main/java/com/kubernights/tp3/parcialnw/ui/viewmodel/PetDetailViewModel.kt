package com.kubernights.tp3.parcialnw.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.domain.usecase.GetDogDetail
import com.kubernights.tp3.parcialnw.domain.usecase.UserAdoptsPetUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
class PetDetailViewModel @Inject constructor(
    private val getDogDetail: GetDogDetail,
    private val adoptPet: UserAdoptsPetUseCase
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()

    private val _operationStatus = MutableLiveData<OperationStatus>()
    val operationStatus: LiveData<OperationStatus>
        get() = _operationStatus

    private val _imageUrls = MutableLiveData<List<String>>() // Changed to ArrayList
    val imageUrls: LiveData<List<String>> = _imageUrls // Changed to ArrayList

    fun getImageUrlsForPet(dog: Dog) {
        viewModelScope.launch {
            try {
                // Invoke GetDogDetailUseCase with the Dog object
                val detailedDog = getDogDetail(dog) // use `invoke` implicitly

                // Use the detailed Dog object to get the image URLs
                val urls = detailedDog.imageUrls ?: arrayListOf<String>()

                // Post the image URLs to the LiveData
                _imageUrls.postValue(urls)
            } catch (e: Exception) {
                // On error, post an empty ArrayList to LiveData
                _imageUrls.postValue(arrayListOf())
                // Log the error or handle it as necessary
                Log.e("ViewModel", "Error getting dog details", e)
            }
        }
    }

    fun petAdoption(petModel: DogModel) {
        viewModelScope.launch {
            try {
                // Execute the use case for user adopting the pet
                adoptPet(petModel.toDomain())
                _operationStatus.postValue(OperationStatus.Success)
            } catch (e: Exception) {
                // Handle exceptions from the use case and update UI
                _operationStatus.postValue(OperationStatus.Failure(e.message ?: "Unknown error"))
            }
        }
    }


    sealed class OperationStatus {
        object Success : OperationStatus()
        class Failure(val message: String) : OperationStatus()
    }
}
