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

    suspend fun getWorkouts(code: String): List<Workout> {
        if (!cacheInitialized) {
            cacheInitialized = true

            val workouts = Firebase.firestore
                .collection("workouts")
                .whereEqualTo("trainingPlanCode", code)
                .get()
                .await()
            workoutsCache.addAll(workouts.toObjects())
            workoutsCache.sortBy { it.date }
        }
        return workoutsCache
    }

    suspend fun createWorkout(
        trainingPlanCode: String,
        date: LocalDate,
        amount: Int,
        unit: String,
        type: String,
        description: String,
        restDay: Boolean
    ): Workout {
        val doc = Firebase.firestore.collection("workouts").document()
        val workout = Workout(
            id = doc.id,
            trainingPlanCode = trainingPlanCode,
            date = date.toString(),
            amount = amount,
            unit = unit,
            type = type,
            description = description,
            restDay = restDay,
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