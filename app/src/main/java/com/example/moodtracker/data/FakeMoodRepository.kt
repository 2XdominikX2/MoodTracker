package com.example.moodtracker.data

import java.util.*

object FakeMoodRepository {
    val moodList = mutableListOf<MoodEntry>()

    fun addMood(entry: MoodEntry) {
        moodList.add(0, entry)
    }

    fun getMoodById(id: UUID): MoodEntry? {
        return moodList.find { it.id == id }
    }
}
