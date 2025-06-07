package com.example.moodtracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.data.MoodType
import java.util.*

class MoodEntryFragment : Fragment() {

    private lateinit var noteEditText: EditText
    private lateinit var moodRadioGroup: RadioGroup
    private lateinit var categorySpinner: Spinner
    private lateinit var sleptCheckBox: CheckBox
    private lateinit var activeCheckBox: CheckBox
    private lateinit var ratingBar: RatingBar
    private lateinit var importantSwitch: Switch
    private lateinit var saveButton: Button

    private val categories = listOf("Szkoła", "Dom", "Znajomi", "Zdrowie", "Inne")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mood_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noteEditText = view.findViewById(R.id.noteEditText)
        moodRadioGroup = view.findViewById(R.id.moodRadioGroup)
        categorySpinner = view.findViewById(R.id.categorySpinner)
        sleptCheckBox = view.findViewById(R.id.sleptCheckBox)
        activeCheckBox = view.findViewById(R.id.activeCheckBox)
        ratingBar = view.findViewById(R.id.ratingBar)
        importantSwitch = view.findViewById(R.id.importantSwitch)
        saveButton = view.findViewById(R.id.saveButton)

        categorySpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)

        // Wczytaj ustawienia użytkownika
        val prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val moodIndex = prefs.getInt("default_mood_index", 1)
        val signature = prefs.getString("signature", "")

        moodRadioGroup.check(
            when (moodIndex) {
                0 -> R.id.radioHappy
                1 -> R.id.radioNeutral
                2 -> R.id.radioSad
                else -> R.id.radioNeutral
            }
        )

        // Wczytaj dane z SharedPreferences
        loadFromPreferences()

        saveButton.setOnClickListener {
            val selectedMood = when (moodRadioGroup.checkedRadioButtonId) {
                R.id.radioHappy -> MoodType.HAPPY
                R.id.radioNeutral -> MoodType.NEUTRAL
                R.id.radioSad -> MoodType.SAD
                else -> MoodType.NEUTRAL
            }

            val entry = MoodEntry(
                mood = selectedMood,
                note = noteEditText.text.toString(),
                category = categorySpinner.selectedItem.toString(),
                sleptWell = sleptCheckBox.isChecked,
                physicallyActive = activeCheckBox.isChecked,
                rating = ratingBar.rating,
                markedImportant = importantSwitch.isChecked,
                usernameSignature = signature ?: ""
            )

            // Dodaj do fake repozytorium
            FakeMoodRepository.addMood(entry)

            // Zapisz dane do SharedPreferences
            saveToPreferences(entry)

            Toast.makeText(requireContext(), "Zapisano nastrój ✅", Toast.LENGTH_SHORT).show()
            resetForm()
        }
    }

    private fun saveToPreferences(entry: MoodEntry) {
        val prefs = requireContext().getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("note", entry.note)
            putString("mood", entry.mood.name)
            putString("category", entry.category)
            putBoolean("sleptWell", entry.sleptWell)
            putBoolean("physicallyActive", entry.physicallyActive)
            putFloat("rating", entry.rating)
            putBoolean("important", entry.markedImportant)
            apply()
        }
    }

    private fun loadFromPreferences() {
        val prefs = requireContext().getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        noteEditText.setText(prefs.getString("note", ""))

        when (prefs.getString("mood", "NEUTRAL")) {
            "HAPPY" -> moodRadioGroup.check(R.id.radioHappy)
            "NEUTRAL" -> moodRadioGroup.check(R.id.radioNeutral)
            "SAD" -> moodRadioGroup.check(R.id.radioSad)
        }

        val category = prefs.getString("category", categories[0])
        val index = categories.indexOf(category)
        if (index >= 0) categorySpinner.setSelection(index)

        sleptCheckBox.isChecked = prefs.getBoolean("sleptWell", false)
        activeCheckBox.isChecked = prefs.getBoolean("physicallyActive", false)
        ratingBar.rating = prefs.getFloat("rating", 0f)
        importantSwitch.isChecked = prefs.getBoolean("important", false)
    }

    private fun resetForm() {
        noteEditText.setText("")
        moodRadioGroup.clearCheck()
        categorySpinner.setSelection(0)
        sleptCheckBox.isChecked = false
        activeCheckBox.isChecked = false
        ratingBar.rating = 0f
        importantSwitch.isChecked = false
    }
}
