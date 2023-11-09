package com.kubernights.tp3.parcialnw.domain.usecase

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.kubernights.tp3.parcialnw.data.DogRepository
import com.kubernights.tp3.parcialnw.data.UserRepository
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.domain.model.Dog
import com.kubernights.tp3.parcialnw.ui.viewmodel.PetDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllDogsUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(): List<Dog> {
        return dogRepository.getDogs()
    }
}

class UserAdoptsPetUseCase @Inject constructor(private val dogRepository: DogRepository, private val userRepository: UserRepository) {
    suspend operator fun invoke(dog: Dog) {
        dogRepository.adoptDog(dog)
        userRepository.updateUserAdoptedPets(dog.id)
    }
}

class GetDogDetail @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(dog: Dog) = dogRepository.getDogById(dog.id)
}

class AddDogUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(dog: Dog) = dogRepository.addDogToFirestore(dog)
}

class GetDogsByBreedUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(breed: String): List<Dog> = dogRepository.getDogsByBreed(breed)
}

class GetDogsByAdoptedStatusUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(adopted: Boolean): List<Dog> = dogRepository.getDogsByAdopted(adopted)
}

class GetDogsByLocationUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(location: String): List<Dog> = dogRepository.getDogsByLocation(location)
}

class GetDogsByGenderUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(gender: Boolean): List<Dog> = dogRepository.getDogsByGender(gender)
}

class GetDogsByAgeUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke(age: Int): List<Dog> = dogRepository.getDogsByAge(age)
}

class DeleteAllDogsUseCase @Inject constructor(private val dogRepository: DogRepository) {
    suspend operator fun invoke() = dogRepository.deleteAllDogs()
}
