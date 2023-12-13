package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.Member
import com.example.trainingplanner.ui.models.TrainingPlan
import com.example.trainingplanner.ui.models.User
import com.example.trainingplanner.ui.models.Workout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object TrainingPlanRepository {
    private val trainingPlanCache = TrainingPlan()
    private var cacheInitialized = false

    suspend fun getTrainingPlan(id: String): TrainingPlan? {
        // 'users' collection contains documents for all users, each containing the user's id and their training plan's id
        val snapshot = Firebase.firestore
            .collection("trainingPlans")
            .document(id)
            .get()
            .await()

        return snapshot.toObject()
    }

    suspend fun createTrainingPlan(
        eventName: String,
        description: String,
        startDate: LocalDate,
        eventDate: LocalDate,
    ): TrainingPlan {
        // create empty workout list for time period
        val workouts = mutableListOf<Workout>()
        var currentDate = startDate
        while (currentDate.isBefore(eventDate) || currentDate.isEqual(eventDate)) {
            workouts.add(Workout(date = currentDate.toString()))
            currentDate = currentDate.plusDays(1)
        }

        val code = generateRandomCode(6)    // TODO: could check for repeat code
        val doc = Firebase.firestore.collection("trainingPlans").document(code)
        val trainingPlan = TrainingPlan(
            code = code,
            eventName = eventName,
            description = description,
            startDate = startDate.toString(),
            eventDate = eventDate.toString(),
            members = listOf(
                Member(
                    userId = UserRepository.getCurrentUserId(),
                    role = "organizer"
                )
            ),
            workouts = workouts
        )
        doc.set(trainingPlan).await()

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