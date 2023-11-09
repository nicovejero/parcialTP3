package com.kubernights.tp3.parcialnw.data.network

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun updateUserAdoptedPets(userId: String, petId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            firestore.runTransaction { transaction ->
                val adoptedPetsRef = firestore.collection("users").document(userId)
                val snapshot = transaction.get(adoptedPetsRef)
                val adoptedPetsList = snapshot.get("adopted") as? MutableList<String> ?: mutableListOf()
                if (!adoptedPetsList.contains(petId)) {
                    adoptedPetsList.add(petId)
                    transaction.update(adoptedPetsRef, "adopted", adoptedPetsList)
                }
            }.await()
            true // Transaction completed successfully
        } catch (e: Exception) {
            Log.e("UserService", "Error updating adopted pets", e)
            false // Transaction failed
        }
    }

    suspend fun updateUserBookmarkedPets(userId: String, petId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            firestore.runTransaction { transaction ->
                val adoptedPetsRef = firestore.collection("users").document(userId)
                val snapshot = transaction.get(adoptedPetsRef)
                val bookmarkedPetsList = snapshot.get("bookmarks") as? MutableList<String> ?: mutableListOf()
                if (!bookmarkedPetsList.contains(petId)) {
                    bookmarkedPetsList.add(petId)
                    transaction.update(adoptedPetsRef, "adopted", bookmarkedPetsList)
                }
            }.await()
            true // Transaction completed successfully
        } catch (e: Exception) {
            Log.e("UserService", "Error updating adopted pets", e)
            false // Transaction failed
        }
    }


}
