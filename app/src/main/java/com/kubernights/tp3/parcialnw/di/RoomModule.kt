//Dependency Injection
package com.kubernights.tp3.parcialnw.di

import android.content.Context
import androidx.room.Room
import com.kubernights.tp3.parcialnw.data.database.DogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DOG_DATABASE_NAME = "dog_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DogDatabase::class.java, DOG_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDogDao(db: DogDatabase) = db.getDogDao()

    @Singleton
    @Provides
    fun provideBreedDao(db: DogDatabase) = db.getBreedDao()
}