package com.example.trainingplanner.ui.repositories
import com.example.trainingplanner.ui.models.User
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
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

    fun getCurrentUserId(): String? {
        return Firebase.auth.currentUser?.uid
    }

    suspend fun getUser(): User {
        val snapshot = Firebase.firestore
            .collection("users")
            .document(getCurrentUserId()!!)
            .get()
            .await()
        return snapshot.toObject()!!
    }

    fun addTrainingPlan(code: String) {
        Firebase.firestore
            .collection("users")
            .document(getCurrentUserId()!!)
            .update("trainingPlanCode", FieldValue.arrayUnion(code))
    }

    fun logout() {
        Firebase.auth.signOut()
    }
}