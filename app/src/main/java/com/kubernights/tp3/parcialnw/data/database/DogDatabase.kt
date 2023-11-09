package com.kubernights.tp3.parcialnw.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kubernights.tp3.parcialnw.data.database.dao.BreedDao
import com.kubernights.tp3.parcialnw.data.database.dao.DogDao
import com.kubernights.tp3.parcialnw.data.database.entities.BreedEntity
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity
import com.kubernights.tp3.parcialnw.data.database.entities.SubBreedEntity

@Database(entities = [DogEntity::class, BreedEntity::class, SubBreedEntity::class], version = 7, exportSchema = false)
abstract class DogDatabase: RoomDatabase() {

    abstract fun getDogDao(): DogDao

    abstract fun getBreedDao(): BreedDao

}