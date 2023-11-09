package com.kubernights.tp3.parcialnw.domain.model

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
