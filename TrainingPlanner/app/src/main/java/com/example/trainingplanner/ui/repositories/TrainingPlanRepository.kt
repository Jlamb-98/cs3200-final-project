package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.Member
import com.example.trainingplanner.ui.models.TrainingPlan
import com.example.trainingplanner.ui.models.Workout
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.LocalDate

object TrainingPlanRepository {
    private var trainingPlanCache = TrainingPlan()
    private var cacheInitialized = false

    suspend fun getTrainingPlan(code: String): TrainingPlan {
        if (!cacheInitialized || code != trainingPlanCache.code) {
            val snapshot = Firebase.firestore
                .collection("trainingPlans")
                .document(code)
                .get()
                .await()
            trainingPlanCache = snapshot.toObject()!!
        }

        return trainingPlanCache
    }

    suspend fun createTrainingPlan(
        eventName: String,
        description: String,
        startDate: LocalDate,
        eventDate: LocalDate,
    ): TrainingPlan {
        // create empty workout list for time period
        val workouts = mutableListOf<Workout?>()
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
            members = mutableListOf(
                Member(
                    userId = UserRepository.getCurrentUserId(),
                    role = "organizer"
                )
            ),
            workouts = workouts
        )
        doc.set(trainingPlan).await()

        UserRepository.addTrainingPlan(code)

        return trainingPlan
    }

    suspend fun updateWorkout(workout: Workout) {
        val workoutsCollectionRef = Firebase.firestore
            .collection("trainingPlans")
            .document(trainingPlanCache.code!!)
            .collection("workouts")

        val document = workoutsCollectionRef
            .whereEqualTo("date", workout.date)
            .get()
            .await()
            .documents
            .firstOrNull()

        if (document != null) {
            val workoutId = document.id
            val workoutDocumentRef = workoutsCollectionRef.document(workoutId)
            workoutDocumentRef.set(workout)
        } else {
            println("No workout found for ${workout.date}")
        }
        val oldWorkoutIndex = trainingPlanCache.workouts.indexOfFirst {
            it?.date == workout.date
        }
        trainingPlanCache.workouts[oldWorkoutIndex] = workout
    }

    suspend fun addMember(code: String, userId: String): Boolean {
        val trainingPlanRef = Firebase.firestore
            .collection("trainingPlans")
            .document(code)
        val document = trainingPlanRef.get().await()

        if (document?.exists() == false) {
            return false
        }

        println(document)
        val newMember = Member(
            userId = userId,
            role = "member"
        )
        trainingPlanRef.update("members", FieldValue.arrayUnion(newMember)).await()
        UserRepository.addTrainingPlan(code)

        return true
    }

    suspend fun checkIfTrainingPlanExists() {

    }

    private fun generateRandomCode(length: Int): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}