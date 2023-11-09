package com.kubernights.tp3.parcialnw.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kubernights.tp3.parcialnw.data.database.entities.BreedEntity
import com.kubernights.tp3.parcialnw.data.database.entities.BreedWithSubBreeds
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreed(breed: BreedEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubBreeds(subBreeds: List<SubBreedEntity>)

    @Transaction
    @Query("SELECT * FROM breeds WHERE breedName = :breedName")
    suspend fun getBreedWithSubBreeds(breedName: String): BreedWithSubBreeds?

    @Transaction
    @Query("SELECT * FROM breeds")
    suspend fun getAllBreedsWithSubBreeds(): List<BreedWithSubBreeds>

    // Additional methods as needed for your functionality
}
