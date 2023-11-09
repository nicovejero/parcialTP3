package com.kubernights.tp3.parcialnw.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kubernights.tp3.parcialnw.data.database.dao.DogDao
import com.kubernights.tp3.parcialnw.data.database.entities.DogEntity

@Database(entities = [DogEntity::class], version = 5, exportSchema = false)
abstract class DogDatabase: RoomDatabase() {

    abstract fun getDogDao(): DogDao

}