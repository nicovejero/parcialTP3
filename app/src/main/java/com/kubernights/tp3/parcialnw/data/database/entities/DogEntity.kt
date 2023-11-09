package com.kubernights.tp3.parcialnw.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kubernights.tp3.parcialnw.data.database.converters.ImageUrlListConverter
import com.kubernights.tp3.parcialnw.domain.model.Dog

@Entity(tableName = "dogs_in_adoption")
@TypeConverters(ImageUrlListConverter::class)
data class DogEntity(
    @PrimaryKey @ColumnInfo(name = "pet_id") val id: String,
    @ColumnInfo(name = "pet_owner_id") val ownerId: String,
    @ColumnInfo(name = "pet_name") val petName: String,
    @ColumnInfo(name = "pet_breed") val petBreed: String,
    @ColumnInfo(name = "pet_sub_breed") val petSubBreed: String,
    @ColumnInfo(name = "pet_location") val petLocation: String,
    @ColumnInfo(name = "pet_age") val petAge: Int,
    @ColumnInfo(name = "pet_weight") val petWeight: Double,
    @ColumnInfo(name = "pet_gender") val petGender: String,
    @ColumnInfo(name = "pet_adopted") val petIsAdopted: Boolean,
    @ColumnInfo(name = "url_image") val imageUrls: List<String>,
    @ColumnInfo(name = "creation_date") val creationDate: Long,
    @ColumnInfo(name = "pet_description") val description: String
)

fun Dog.toDatabase() = DogEntity(
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
    description = description,
    petWeight = petWeight
)