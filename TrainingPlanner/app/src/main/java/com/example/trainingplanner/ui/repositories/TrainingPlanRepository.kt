package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.TrainingPlan
import com.example.trainingplanner.ui.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object TrainingPlanRepository {
//    private val workoutsCache = mutableListOf<Workout>()
//    private var cacheInitialized = false

    suspend fun getTrainingPlan(): TrainingPlan? {
        // 'users' collection contains documents for all users, each containing the user's id and their training plan's id
        // or can I somehow search the trainingPlan collections deeper for member id's???
        // i.e. `.contains("members", UserRepository.getCurrentUserId()!!)
        val snapshot = Firebase.firestore
            .collection("trainingPlans")
            .document(UserRepository.getUserTrainingPlan()!!)
            .get()
            .await()

//        if (!cacheInitialized) {
//            cacheInitialized = true
//            val snapshot = Firebase.firestore
//                .collection("workouts")
//                .whereEqualTo("userId", UserRepository.getCurrentUserId())
////                .whereArrayContains("members", UserRepository.getCurrentUserId()!!)
//                .get()
//                .await()
//            // this document will contain more than just workouts...
//            // it has all data about the training plan
//            workoutsCache.addAll(snapshot.toObjects())
//        }
        return snapshot.toObject()
    }

    suspend fun createTrainingPlan(
        name: String,
        description: String,
        eventName: String,
        eventDate: String,
        startDate: String,
    ): TrainingPlan {
        val doc = Firebase.firestore.collection("trainingPlans").document()
        val code = generateRandomCode(6)    // TODO: could check for repeat code
        val trainingPlan = TrainingPlan(
            id = doc.id,
            organizerId = UserRepository.getCurrentUserId(),
            code = code,
            name = name,
            description = description,
            eventName = eventName,
            eventDate = eventDate,
            startDate = startDate
        )
        doc.set(trainingPlan).await()
        val user = UserRepository.getUser()
        val newUser = user.copy(
            trainingPlanId = doc.id,
            role = "organizer"
        )
        Firebase.firestore
            .collection("users")
            .document(UserRepository.getCurrentUserId()!!)
            .set(newUser)
            .await()
//        workoutsCache.add(workout)
        return trainingPlan
    }

    private fun generateRandomCode(length: Int): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

//    suspend fun updateWorkout(workout: Workout) {
//        Firebase.firestore
//            .collection("workouts")
//            .document(workout.id!!)
//            .set(workout)
//            .await()
//
//        val oldWorkoutIndex = workoutsCache.indexOfFirst {
//            it.id == workout.id
//        }
//        workoutsCache[oldWorkoutIndex] = workout
//    }

}