package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository

class MoodHistoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mood_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyText = view.findViewById<TextView>(R.id.historyTextView)
        val entries = FakeMoodRepository.getMoods()

        if (entries.isEmpty()) {
            historyText.text = "Brak zapisanych wpisów 😢"
        } else {
            historyText.text = entries.joinToString("\n\n") { entry ->
                """
                😄 Nastrój: ${entry.mood}
                📝 Notatka: ${entry.note}
                📁 Kategoria: ${entry.category}
                🌙 Spał dobrze? ${if (entry.sleptWell) "Tak" else "Nie"}
                💪 Aktywny? ${if (entry.physicallyActive) "Tak" else "Nie"}
                ⭐ Ocena: ${entry.rating}
                ❗ Ważny: ${if (entry.markedImportant) "Tak" else "Nie"}
                ✍️ Podpis: ${entry.usernameSignature}
                """.trimIndent()
            }
        }
    }
}
