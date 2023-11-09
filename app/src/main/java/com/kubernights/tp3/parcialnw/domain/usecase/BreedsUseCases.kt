package com.kubernights.tp3.parcialnw.domain.usecase

import com.kubernights.tp3.parcialnw.data.BreedRepository
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val breedRepository: BreedRepository) {
    suspend operator fun invoke(): List<BreedWithSubBreeds> {
        // Error handling, loading states, and any additional logic would be handled here
        return breedRepository.getAllBreedsWithSubBreeds()
    }
}

class GetSubBreedsUseCase @Inject constructor(private val breedRepository: BreedRepository) {
    suspend operator fun invoke(parentBreedId: Long): List<SubBreedEntity> {
        // Error handling, loading states, and any additional logic would be handled here
        return breedRepository.getSubBreedByParentId(parentBreedId)
    }
}