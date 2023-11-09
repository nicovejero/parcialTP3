package com.kubernights.tp3.parcialnw.domain.model

import androidx.room.ColumnInfo
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.data.model.BreedsApiResponse

data class Dog(
    val id: String,
    val ownerId: String,
    val petName: String,
    val petBreed: String,
    val petSubBreed: String,
    val petLocation: String,
    val petAge: Int,
    val petGender: Boolean,
    val petIsAdopted: Boolean,
    val imageUrls: List<String>,
    val creationDate: Long,
    val description: String
)

/*
fun BreedsApiResponse.toDomainModelList(): List<Dog> {
    return this.message.map { (breedName, subBreeds) ->
        Breed(
            breedName = breedName,
            subBreeds = subBreeds
        )
    }
}

// Converts BreedEntity from the database layer to Breed domain model
fun BreedEntity.toDomain(subBreedsList: List<String>): Breed {
    return Breed(
        breedName = this.breedName,
        subBreeds = subBreedsList
    )
}

// Extension function to serialize a list of sub-breeds into JSON for storage
fun Breed.toEntity(): BreedEntity {
    return BreedEntity(
        breedName = this.breedName,
    )
}

fun Breed.toDatabase(): BreedEntity {
    // Assuming you have a constructor in BreedEntity that takes the necessary parameters
    return BreedEntity(
        breedName = this.breedName,
    )
}
*/