package com.kubernights.tp3.parcialnw.data

import com.kubernights.tp3.parcialnw.data.database.dao.DogDao
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.data.network.DogService
import com.kubernights.tp3.parcialnw.domain.SessionManagerInterface
import com.kubernights.tp3.parcialnw.domain.model.Dog
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val dogDao: DogDao,
    private val remote: DogService,
    private val sessionManager: SessionManagerInterface
) {

    suspend fun getAllDogsFromFirebase(): List<Dog> {
        val response: List<DogModel> = remote.getAllDogs()
        return response.toList().map { it.toDomain() }
    }

    suspend fun getAllDogsFromDatabase():List<Dog>{
        val response: List<DogEntity> = dogDao.getAllDogs()
        return response.map { it.toDomainModel() }
    }

    /*
        // If remote fetch is successful and returns dogs
        if (remoteDogs.isNotEmpty()) {
            // Convert to domain model
            val domainDogs = remoteDogs.map { it.toDomainModel() }
            // Insert fetched dogs into the database, replacing the old cache
            dogDao.deleteAllDogs()
            insertAllDogs(domainDogs)
            // Return the newly updated data from the remote
            return domainDogs
        } else {
            // If remote fetch fails or is empty, fall back to the local cache
            return dogDao.getAllDogs().map { it.toDomainModel() }
        }
    }
*/

    suspend fun addDogToFirestore(dog: Dog) {
        val userId = sessionManager.currentUserId
        val dogModel = dog.toDataModel()
        remote.addDogToFirestore(dogModel, userId)
    }

    suspend fun getDogsByBreed(breed: String): List<Dog> {
        return dogDao.getDogsByBreed(breed).map { it.toDomainModel() }
    }

    suspend fun getDogsByAdopted(adopted: Boolean): List<Dog> {
        return dogDao.getDogsByAdopted(adopted).map { it.toDomainModel() }
    }

    suspend fun getDogsByLocation(location: String): List<Dog> {
        return dogDao.getDogsByLocation(location).map { it.toDomainModel() }
    }

    suspend fun getDogsByGender(gender: Boolean): List<Dog> {
        return dogDao.getDogsByGender(gender).map { it.toDomainModel() }
    }

    suspend fun getDogsByAge(age: Int): List<Dog> {
        return dogDao.getDogsByAge(age).map { it.toDomainModel() }
    }

    suspend fun insertAllDogs(dogs: List<DogEntity>) {
        dogDao.insertAllDogs(dogs)
    }

    suspend fun deleteAllDogs() {
        dogDao.deleteAllDogs()
    }

    private fun DogEntity.toDomainModel(): Dog {
        return Dog(
            id = this.id,
            ownerId = this.ownerId,
            petName = this.petName,
            petBreed = this.petBreed,
            petSubBreed = this.petSubBreed,
            petLocation = this.petLocation,
            petAge = this.petAge,
            petGender = this.petGender,
            petIsAdopted = this.petIsAdopted,
            imageUrls = this.imageUrls,
            creationDate = this.creationDate,
            description = this.description
        )
    }

    private fun Dog.toDataModel(): DogModel {
        return DogModel(
            petId = this.id,
            petOwner = this.ownerId,
            petName = this.petName,
            petBreed = this.petBreed,
            petSubBreed = this.petSubBreed,
            petLocation = this.petLocation,
            petAge = this.petAge,
            petGender = this.petGender,
            petAdopted = this.petIsAdopted,
            urlImage = this.imageUrls,
            petDescripcion = this.description,
            creationTimestamp = this.creationDate
        )
    }
}
