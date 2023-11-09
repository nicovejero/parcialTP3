package com.kubernights.tp3.parcialnw.data.network

import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.kubernights.tp3.parcialnw.data.model.DogModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DogService @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    suspend fun getAllDogs(): List<DogModel> = withContext(Dispatchers.IO) {
        try {
            val result = firestore.collection("pets").get().await()
            val dogs = mutableListOf<DogModel>()
            for (document in result) {
                Log.d("Firestore", "${document.id} => ${document.data}")
                val dog = document.toObject(DogModel::class.java)
                dogs.add(dog)
            }
            dogs
        } catch (e: Exception) {
            Log.w("Firestore", "Error getting documents.", e)
            emptyList() // Return an empty list in case of error
        }
    }

    suspend fun addDogToFirestore(petModel: DogModel, userId: String?) = withContext(Dispatchers.IO) {
        if (userId == null) {
            throw IllegalArgumentException("User ID must not be null when adding a pet")
        }
        Log.e("Firestore", "Adding pet to Firestore")
        // Set the creation timestamp here before adding to database
        petModel.creationTimestamp = System.currentTimeMillis()

        firestore.collection("pets")
            .add(petModel.toMap()) // Ensure PetModel has a toMap() method
            .addOnSuccessListener { documentReference ->
                val petId = documentReference.id

                petModel.petOwner = userId
                petModel.petId = petId

                firestore.collection("pets").document(petId)
                    .set(petModel.toMap())
                    .addOnSuccessListener {
                        Log.d("Firestore", "Document with ID: $petId updated successfully")
                        // Further success handling
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error updating document", e)
                        // Handle failure
                    }
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding document", e)
                // Handle failure
            }
    }
}