package com.kubernights.tp3.parcialnw.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity

@Dao
interface DogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(dog: DogEntity): Long

    @Query("SELECT * FROM dogs_in_adoption")
    suspend fun getAllDogs(): List<DogEntity>

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_id = :id")
    suspend fun getDogById(id: String): DogEntity

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_breed = :breed")
    suspend fun getDogsByBreed(breed: String): List<DogEntity>

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_adopted = :adopted")
    suspend fun getDogsByAdopted(adopted: Boolean): List<DogEntity>

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_location = :location")
    suspend fun getDogsByLocation(location: String): List<DogEntity>

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_gender = :gender")
    suspend fun getDogsByGender(gender: Boolean): List<DogEntity>

    @Query("SELECT * FROM dogs_in_adoption WHERE pet_age = :age")
    suspend fun getDogsByAge(age: Int): List<DogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDogs(dogs: List<DogEntity>)

    @Query("DELETE FROM dogs_in_adoption")
    suspend fun deleteAllDogs()

    // Additional queries can be added here as needed
}
