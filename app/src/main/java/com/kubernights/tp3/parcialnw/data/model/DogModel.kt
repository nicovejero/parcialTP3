package com.kubernights.tp3.parcialnw.data.model

import android.os.Parcel
import android.os.Parcelable
import com.kubernights.tp3.parcialnw.domain.model.Dog

data class DogModel(
    var petId: String = "",
    val petName: String = "",
    val petBreed: String = "",
    val petSubBreed: String = "",
    val urlImage: List<String> = emptyList(),
    val petAge: Int = 0,
    val petWeight: Double = 0.0,
    val petGender: String = "",
    var petOwner: String = "",
    var petLocation: String = "",
    val petAdopted: Boolean = false,
    var creationTimestamp: Long = 0,
    var petDescripcion: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        mutableListOf<String>().apply { parcel.readStringList(this) },
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Long::class.java.classLoader) as Long, // Updated line
        parcel.readString()?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        writeString(petId)
        writeString(petName)
        writeString(petBreed)
        writeString(petSubBreed)
        writeStringList(urlImage)
        writeInt(petAge)
        writeDouble(petWeight)
        writeString(petGender)
        writeString(petOwner)
        writeString(petLocation)
        writeByte(if (petAdopted) 1 else 0)
        writeValue(creationTimestamp) // Updated line
        writeString(petDescripcion)
    }

    fun toMap(): Map<String, Any> {
        return mapOf(
            "petId" to petId,
            "petName" to petName,
            "petBreed" to petBreed,
            "petSubBreed" to petSubBreed,
            "urlImage" to urlImage,
            "petAge" to petAge,
            "petWeight" to petWeight,
            "petGender" to petGender,
            "petOwner" to petOwner,
            "petLocation" to petLocation,
            "petAdopted" to petAdopted,
            "creationTimestamp" to creationTimestamp,
            "petDescripcion" to petDescripcion
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    fun toDomain(): Dog {
        // Map the remote model to your domain model
        return Dog(
            id = this.petId,
            ownerId = this.petOwner,
            petName = this.petName,
            petBreed = this.petBreed,
            petSubBreed = this.petSubBreed,
            petLocation = this.petLocation,
            petAge = this.petAge,
            petGender = this.petGender,
            petIsAdopted = this.petAdopted,
            imageUrls = this.urlImage,
            creationDate = this.creationTimestamp,
            description = this.petDescripcion
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
