package com.kubernights.tp3.parcialnw.domain

import com.kubernights.tp3.parcialnw.data.DogRepository
import com.kubernights.tp3.parcialnw.data.UserRepository
import com.kubernights.tp3.parcialnw.domain.model.Dog
import javax.inject.Inject

class UpdateUserAdoptedPetsUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(petId: String): Boolean{
            return repository.updateUserAdoptedPets(petId)
        }
}