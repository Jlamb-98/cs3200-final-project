package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object WorkoutsRepository {
    private val workoutsCache = mutableListOf<Workout>()
    private var cacheInitialized = false

    suspend fun getWorkouts(): List<Workout> {
        // TODO
        // trainingPlan collection has a field of members, check the collections for which one has this member's id

        return workoutsCache
    }

    suspend fun createWorkout(
        groupId: String,
        title: String,
        description: String,
        date: LocalDate,
        userCompletion: Boolean,
        memberCompletion: String
    ): Workout {
        val doc = Firebase.firestore.collection("workouts").document()
        val workout = Workout(
            id = doc.id,
            groupId = groupId,  //TODO: can I pass this value in or do I need to get it from Firebase??
            title = title,
            description = description,
            date = date,
            userCompletion = false,
            memberCompletion = mutableListOf(false) //TODO: does the number of members matter here??
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