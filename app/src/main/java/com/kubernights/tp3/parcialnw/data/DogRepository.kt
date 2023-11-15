package com.kubernights.tp3.parcialnw.data

import com.kubernights.tp3.parcialnw.data.database.dao.BreedDao
import com.kubernights.tp3.parcialnw.data.database.dao.DogDao
import com.kubernights.tp3.parcialnw.data.database.entities.BreedEntity
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity
import com.kubernights.tp3.parcialnw.data.database.entities.toDatabase
import com.kubernights.tp3.parcialnw.data.model.BreedsApiResponse
import com.kubernights.tp3.parcialnw.data.model.DogModel
import com.kubernights.tp3.parcialnw.data.network.BreedService
import com.kubernights.tp3.parcialnw.data.network.DogService
import com.kubernights.tp3.parcialnw.data.network.UserService
import com.kubernights.tp3.parcialnw.domain.SessionManagerInterface
import com.kubernights.tp3.parcialnw.domain.model.Dog
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val dogDao: DogDao,
    private val remoteDog: DogService,
    private val remoteUser: UserService,
    private val sessionManager: SessionManagerInterface
) {

    suspend fun adoptDog(dog: Dog): Boolean {
        var response = false
        val userId = sessionManager.currentUserId
        if (userId != null) {
            response = remoteUser.updateUserAdoptedPets(userId, dog.id)
        }


        return response
    }

    suspend fun getDogs(): List<Dog> {
        return try {
            val firebaseDogs = getAllDogsFromFirebase()
            if (firebaseDogs.isNotEmpty()) {
                dogDao.insertAllDogs(firebaseDogs.map { it.toDatabase() })
                firebaseDogs
            } else {
                getAllDogsFromDatabase()
            }
        } catch (e: Exception) {
            getAllDogsFromDatabase()
        }
    }

    suspend fun getAllDogsFromFirebase(): List<Dog> {
        val response: List<DogModel> = remoteDog.getAllDogs()
        return response.toList().map { it.toDomain() }
    }

    suspend fun getAllDogsFromDatabase():List<Dog>{
        val response: List<DogEntity> = dogDao.getAllDogs()
        return response.map { it.toDomainModel() }
    }

    suspend fun getDogById(id: String): Dog {
        return dogDao.getDogById(id).toDomainModel()
    }

    suspend fun addDogToFirestore(dog: Dog) {
        val userId = sessionManager.currentUserId
        val dogModel = dog.toDataModel()
        remoteDog.addDogToFirestore(dogModel, userId)
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
            petWeight = this.petWeight,
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