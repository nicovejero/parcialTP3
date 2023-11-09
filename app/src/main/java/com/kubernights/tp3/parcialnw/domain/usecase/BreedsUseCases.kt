package com.kubernights.tp3.parcialnw.domain.usecase

import com.kubernights.tp3.parcialnw.data.BreedRepository
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val breedRepository: BreedRepository) {
    suspend operator fun invoke(): List<BreedWithSubBreeds> {
        return breedRepository.getAllBreedsWithSubBreeds()
    }
}

class GetSubBreedsUseCase @Inject constructor(private val breedRepository: BreedRepository) {
    suspend operator fun invoke(parentBreedId: Long): List<SubBreedEntity> {
        return breedRepository.getSubBreedByParentId(parentBreedId)
    }
}