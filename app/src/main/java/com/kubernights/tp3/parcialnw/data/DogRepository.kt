package com.kubernights.tp3.parcialnw.data

import com.kubernights.tp3.parcialnw.data.database.dao.DogDao
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.domain.model.Dog
import javax.inject.Inject

class DogRepository @Inject constructor(
    private val dogDao: DogDao
) {
    suspend fun getAllDogs(): List<Dog> {
        return dogDao.getAllDogs().map { entity ->
            Dog(
                id = entity.id,
                ownerId = entity.ownerId,
                petName = entity.petName,
                petBreed = entity.petBreed,
                petSubBreed = entity.petSubBreed,
                petLocation = entity.petLocation,
                petAge = entity.petAge,
                petGender = entity.petGender,
                petIsAdopted = entity.petIsAdopted,
                imageUrls = entity.imageUrls,
                creationDate = entity.creationDate,
                description = entity.description
            )
        }
    }

    suspend fun addDog(dog: Dog) {
        dogDao.insertDog(
            DogEntity(
                id = dog.id,
                ownerId = dog.ownerId,
                petName = dog.petName,
                petBreed = dog.petBreed,
                petSubBreed = dog.petSubBreed,
                petLocation = dog.petLocation,
                petAge = dog.petAge,
                petGender = dog.petGender,
                petIsAdopted = dog.petIsAdopted,
                imageUrls = dog.imageUrls,
                creationDate = dog.creationDate,
                description = dog.description
            )
        )
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

    suspend fun insertAllDogs(dogs: List<Dog>) {
        val dogEntities = dogs.map { dog ->
            DogEntity(
                id = dog.id,
                ownerId = dog.ownerId,
                petName = dog.petName,
                petBreed = dog.petBreed,
                petSubBreed = dog.petSubBreed,
                petLocation = dog.petLocation,
                petAge = dog.petAge,
                petGender = dog.petGender,
                petIsAdopted = dog.petIsAdopted,
                imageUrls = dog.imageUrls,
                creationDate = dog.creationDate,
                description = dog.description
            )
        }
        dogDao.insertAllDogs(dogEntities)
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
}
