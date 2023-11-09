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
    /*suspend fun parseAndInsertBreedData(response: BreedsApiResponse, breedDao: BreedDao) {
        response.message.forEach { (breedName, subBreeds) ->
            val breedEntity = BreedEntity(breedName = breedName)
            val breedId = breedDao.insertBreed(breedEntity)

            val subBreedEntities = subBreeds.map { subBreedName ->
                SubBreedEntity(subBreedId = breedId.toInt(), subBreedName = subBreedName)
            }
            breedDao.insertSubBreeds(subBreedEntities)
        }
    }
*/
    suspend fun getAllBreedsWithSubBreeds(): List<BreedWithSubBreeds> {
        // This might interact with the database or make a network call
        return breedDao.getAllBreedsWithSubBreeds()
    }

}