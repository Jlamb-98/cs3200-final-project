package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object WorkoutsRepository {
    private val workoutsCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getWorkouts(): List<Workout> {
        if (!cacheInitialized) {
            cacheInitialized = true
            val snapshot = Firebase.firestore
                .collection("users")
                .document(UserRepository.getCurrentUserId()!!)
//                .whereArrayContains("members", UserRepository.getCurrentUserId()!!)
                .get()
                .await()
            val trainingPlanId = snapshot.get("trainingPlanId").toString()

            val workouts = Firebase.firestore
                .collection("workouts")
                .whereEqualTo("trainingPlanId", trainingPlanId)
                .get()
                .await()
            workoutsCache.addAll(workouts.toObjects())
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
//            id = doc.id,
//            userId = UserRepository.getCurrentUserId(),
//            groupId = groupId,  //TODO: can I pass this value in or do I need to get it from Firebase??
            title = title,
            description = description,
            date = date.toString(),
            membersCompleted = mutableListOf(""),
        )
        doc.set(workout).await()
        workoutsCache.add(workout)
        return workout
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