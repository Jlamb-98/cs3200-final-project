package com.example.trainingplanner.ui.repositories
import com.example.trainingplanner.ui.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class SignUpException(message: String?): RuntimeException(message)
class SignInException(message: String?): RuntimeException(message)

object UserRepository {
    suspend fun createUser(email: String, password: String, username: String) {
        try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthException) {
            throw SignUpException(e.message)
        } finally {
            val user = User(
                id = getCurrentUserId(),
                username = username
            )
            Firebase.firestore
                .collection("users")
                .document(getCurrentUserId()!!)
                .set(user)
                .await()
        }
    }

    suspend fun loginUser(email: String, password: String) {
        try {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseException) {
            throw SignInException(e.message)
        }
    }

    suspend fun editUsername(username: String) {
        val user = User(
            id = getCurrentUserId(),
            username = username
        )
        Firebase.firestore
            .collection("users")
            .document(Firebase.auth.currentUser?.uid!!)
            .set(user)
            .await()
    }

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}