package com.kubernights.tp3.parcialnw.domain.model

import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.data.model.DogModel

data class Dog(
    val id: String,
    val ownerId: String,
    val petName: String,
    val petBreed: String,
    val petSubBreed: String,
    val petLocation: String,
    val petAge: Int,
    val petGender: String,
    val petIsAdopted: Boolean,
    val imageUrls: List<String>,
    val creationDate: Long,
    val description: String
)

fun DogModel.toDomain() = Dog(
    id = petId,
    ownerId = petOwner,
    petName = petName,
    petBreed = petBreed,
    petSubBreed = petSubBreed,
    petLocation = petLocation,
    petAge = petAge,
    petGender = petGender,
    petIsAdopted = petAdopted,
    imageUrls = urlImage,
    creationDate = creationTimestamp,
    description = petDescripcion
)

fun DogEntity.toDomain() = Dog(
    id = id,
    ownerId = ownerId,
    petName = petName,
    petBreed = petBreed,
    petSubBreed = petSubBreed,
    petLocation = petLocation,
    petAge = petAge,
    petGender = petGender,
    petIsAdopted = petIsAdopted,
    imageUrls = imageUrls,
    creationDate = creationDate,
    description = description
)