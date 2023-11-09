package com.kubernights.tp3.parcialnw.data

import android.util.Log
import android.widget.Toast
import com.kubernights.tp3.parcialnw.data.network.UserService
import com.kubernights.tp3.parcialnw.domain.SessionManagerInterface
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteUser: UserService,
    private val sessionManager: SessionManagerInterface
    ) {

    private val userId = sessionManager.currentUserId?:""

    suspend fun updateUserAdoptedPets(petId: String): Boolean {
        Log.e("petId", "petId")
        val response: Boolean = remoteUser.updateUserAdoptedPets(userId, petId)
        return response
    }

}
