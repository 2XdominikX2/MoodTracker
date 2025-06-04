package com.example.moodtracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.moodtracker.R

class SettingsFragment : Fragment() {

    private lateinit var darkModeSwitch: Switch
    private lateinit var defaultMoodSpinner: Spinner
    private lateinit var signatureEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch)
        defaultMoodSpinner = view.findViewById(R.id.defaultMoodSpinner)
        signatureEditText = view.findViewById(R.id.signatureEditText)
        saveButton = view.findViewById(R.id.saveSettingsButton)

        val moods = listOf("Wesoły", "Przeciętny", "Smutny")
        defaultMoodSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, moods)

        val prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        darkModeSwitch.isChecked = prefs.getBoolean("dark_mode", false)
        defaultMoodSpinner.setSelection(prefs.getInt("default_mood_index", 1))
        signatureEditText.setText(prefs.getString("signature", ""))

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            editor.putBoolean("dark_mode", isChecked).apply()
        }

        saveButton.setOnClickListener {
            editor.putInt("default_mood_index", defaultMoodSpinner.selectedItemPosition)
            editor.putString("signature", signatureEditText.text.toString())
            editor.apply()
            Toast.makeText(requireContext(), "Ustawienia zapisane", Toast.LENGTH_SHORT).show()
        }
    }
}
