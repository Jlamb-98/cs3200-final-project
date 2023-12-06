package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.User
import com.example.trainingplanner.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object WorkoutsRepository {
    private val workoutsCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getWorkouts(): List<Workout> {
        // 'users' collection contains documents for all users, each containing the user's id and their training plan's id
        // or can I somehow search the trainingPlan collections deeper for member id's???
        // i.e. `.contains("members", UserRepository.getCurrentUserId()!!)
        val document = Firebase.firestore
            .collection("users")
            .document(UserRepository.getCurrentUserId()!!)
            .get()
            .await()
        val user: User? = document.toObject()

        if (!cacheInitialized) {
            cacheInitialized = true
            val snapshot = Firebase.firestore
                .collection("workouts")
                .whereEqualTo("id", user?.trainingPlanId)
//                .whereArrayContains("members", UserRepository.getCurrentUserId()!!)
                .get()
                .await()
            // this document will contain more than just workouts...
            // it has all data about the training plan
            workoutsCache.addAll(snapshot.toObjects())
        }

        return workoutsCache
    }

    suspend fun createWorkout(
//        groupId: String,
        title: String,
        description: String,
        date: LocalDate
    ): Workout {
        val doc = Firebase.firestore.collection("workouts").document()
        val workout = Workout(
            id = doc.id,
//            groupId = groupId,  //TODO: can I pass this value in or do I need to get it from Firebase??
            title = title,
            description = description,
            date = date,
            userCompleted = false,
//            memberCompletion = mutableListOf(false) //TODO: does the number of members matter here??
        )
        doc.set(workout).await()
        workoutsCache.add(workout)
        return workout
    }

    suspend fun updateWorkout(workout: Workout) {
        Firebase.firestore
            .collection("workouts")
            .document(workout.id!!)
            .set(workout)
            .await()

        val oldWorkoutIndex = workoutsCache.indexOfFirst {
            it.id == workout.id
        }
        workoutsCache[oldWorkoutIndex] = workout
    }
}