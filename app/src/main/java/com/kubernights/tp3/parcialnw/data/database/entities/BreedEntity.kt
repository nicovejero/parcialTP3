package com.kubernights.tp3.parcialnw.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.kubernights.tp3.parcialnw.domain.model.Dog

@Entity(tableName = "breeds")
data class BreedEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "breedName") val breedName: String,
)

@Entity(tableName = "subbreeds")
data class SubBreedEntity(
    @PrimaryKey(autoGenerate = true) val subBreedId: Long = 0,
    @ColumnInfo(name = "parentBreedId") val parentBreedId: Long,
    @ColumnInfo(name = "subBreedName") val subBreedName: String
)

data class BreedWithSubBreeds(
    @Embedded val breed: BreedEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentBreedId"
    )
    val subBreeds: List<SubBreedEntity>
)
