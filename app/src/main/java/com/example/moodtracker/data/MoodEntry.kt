package com.example.moodtracker.data

import java.util.*

data class MoodEntry(
    val id: UUID = UUID.randomUUID(),
    val date: Date = Date(),
    val mood: MoodType,
    val note: String,
    val category: String,
    val sleptWell: Boolean,
    val physicallyActive: Boolean,
    val rating: Float,
    val markedImportant: Boolean,
    val usernameSignature: String = ""
)

enum class MoodType {
    HAPPY, NEUTRAL, SAD
}
