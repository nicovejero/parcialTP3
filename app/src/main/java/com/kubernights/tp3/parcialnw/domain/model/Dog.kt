package com.kubernights.tp3.parcialnw.domain.model

import android.os.Parcel
import android.os.Parcelable
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
    val petWeight: Double,
    val petGender: String,
    val petIsAdopted: Boolean,
    val imageUrls: List<String>,
    val creationDate: Long,
    val description: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        mutableListOf<String>().apply { parcel.readStringList(this) },
        parcel.readValue(Long::class.java.classLoader) as Long, // Updated line
        parcel.readString()?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(id)
        writeString(ownerId)
        writeString(petName)
        writeString(petBreed)
        writeString(petSubBreed)
        writeString(petLocation)
        writeInt(petAge)
        writeDouble(petWeight)
        writeString(petGender)
        writeByte(if (petIsAdopted) 1 else 0)
        writeString(petLocation)
        mutableListOf<String>().apply { parcel.readStringList(this) }
        writeValue(creationDate) // Updated line
        writeString(description)
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "petId" to id,
            "petName" to petName,
            "petBreed" to petBreed,
            "petSubBreed" to petSubBreed,
            "urlImage" to imageUrls,
            "petAge" to petAge,
            "petWeight" to petWeight,
            "petGender" to petGender,
            "petOwner" to ownerId,
            "petLocation" to petLocation,
            "petAdopted" to petIsAdopted,
            "creationTimestamp" to creationDate,
            "petDescripcion" to description
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toDomain(): Dog {
        // Map the remote model to your domain model
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

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DogModel> = object : Parcelable.Creator<DogModel> {
            override fun createFromParcel(parcel: Parcel): DogModel {
                return DogModel(parcel)
            }

            override fun newArray(size: Int): Array<DogModel?> {
                return arrayOfNulls(size)
            }
        }
    }
}


fun Dog.toModel() = DogModel(
    petId = id,
    petName = petName,
    petBreed = petBreed,
    petSubBreed = petSubBreed,
    urlImage = imageUrls,
    petAge = petAge,
    petWeight = petWeight,
    petGender = petGender,
    petOwner = ownerId,
    petLocation = petLocation,
    petAdopted = petIsAdopted,
    creationTimestamp = creationDate,
    petDescripcion = description
)


fun DogModel.toDomain() = Dog(
    id = petId,
    ownerId = petOwner,
    petName = petName,
    petBreed = petBreed,
    petSubBreed = petSubBreed,
    petLocation = petLocation,
    petAge = petAge,
    petWeight = petWeight,
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
    petWeight = petWeight,
    petGender = petGender,
    petIsAdopted = petIsAdopted,
    imageUrls = imageUrls,
    creationDate = creationDate,
    description = description
)

