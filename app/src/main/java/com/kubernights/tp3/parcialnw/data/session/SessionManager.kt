
package com.kubernights.tp3.parcialnw.data.session

import com.google.firebase.auth.FirebaseAuth
import com.kubernights.tp3.parcialnw.domain.SessionManagerInterface
import javax.inject.Inject

class SessionManager @Inject constructor() : SessionManagerInterface {
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override val currentUserId: String?
        get() {
            return userId
        }
}
