package com.kubernights.tp3.parcialnw.domain.usecase

import com.kubernights.tp3.parcialnw.data.DogRepository
import com.kubernights.tp3.parcialnw.domain.model.Dog
import javax.inject.Inject

class GetAllDogsUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(): List<Dog> = repository.getAllDogs()
}

class AddDogUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(dog: Dog) = repository.addDog(dog)
}

class GetDogsByBreedUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(breed: String): List<Dog> = repository.getDogsByBreed(breed)
}

class GetDogsByAdoptedStatusUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(adopted: Boolean): List<Dog> = repository.getDogsByAdopted(adopted)
}

class GetDogsByLocationUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(location: String): List<Dog> = repository.getDogsByLocation(location)
}

class GetDogsByGenderUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(gender: Boolean): List<Dog> = repository.getDogsByGender(gender)
}

class GetDogsByAgeUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(age: Int): List<Dog> = repository.getDogsByAge(age)
}

class InsertAllDogsUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke(dogs: List<Dog>) = repository.insertAllDogs(dogs)
}

class DeleteAllDogsUseCase @Inject constructor(private val repository: DogRepository) {
    suspend operator fun invoke() = repository.deleteAllDogs()
}
