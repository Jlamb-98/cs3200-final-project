package com.example.trainingplanner.ui.repositories

import com.example.trainingplanner.ui.models.Member
import com.example.trainingplanner.ui.models.TrainingPlan
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
        // create trainingPlan document from code
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
            )
        )
        doc.set(trainingPlan).await()

        // create workout list for time period
        var currentDate = startDate
        while (currentDate.isBefore(eventDate) || currentDate.isEqual(eventDate)) {
            WorkoutsRepository.createWorkout(
                code,
                currentDate,
                2,
                "mile",
                "run",
                "go fast",
                false
            )
            currentDate = currentDate.plusDays(1)
        }

        UserRepository.addTrainingPlan(code)

        return trainingPlan
    }

    suspend fun updateTrainingPlan(trainingPlan: TrainingPlan) {
        // TODO: rewrite entire training plan
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

    private fun generateRandomCode(length: Int): String {
        val allowedChars = ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

}