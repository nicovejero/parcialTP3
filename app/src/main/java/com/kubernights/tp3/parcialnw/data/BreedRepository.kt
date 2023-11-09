package com.kubernights.tp3.parcialnw.data

import com.kubernights.tp3.parcialnw.data.database.dao.BreedDao
import com.kubernights.tp3.parcialnw.data.database.entities.BreedEntity
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import com.kubernights.tp3.parcialnw.data.model.BreedsApiResponse
import com.kubernights.tp3.parcialnw.data.network.BreedService
import javax.inject.Inject

class BreedRepository @Inject constructor(
    private val breedDao: BreedDao,
    private val remote: BreedService
) {
    suspend fun refreshBreeds() {
        val response = remote.getBreeds()
        insertBreedsIntoDatabase(response)
    }

    private suspend fun insertBreedsIntoDatabase(response: BreedsApiResponse) {
        response.message.forEach { (breedName, subBreeds) ->
            val breedEntity = BreedEntity(breedName = breedName)
            val breedId = breedDao.insertBreed(breedEntity)

            val subBreedEntities = subBreeds.map { subBreedName ->
                SubBreedEntity(subBreedName = subBreedName, parentBreedId = breedId)
            }
            breedDao.insertSubBreeds(subBreedEntities)
        }
    }

    suspend fun getSubBreedByParentId(breedId: Long): List<SubBreedEntity> {
        return breedDao.getSubBreedByParentId(breedId)
    }

    suspend fun getAllBreedsWithSubBreeds(): List<BreedWithSubBreeds> {
             refreshBreeds()
        return breedDao.getAllBreedsWithSubBreeds()
    }
}