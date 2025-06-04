package com.example.moodtracker.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moodtracker.R
import com.example.moodtracker.data.FakeMoodRepository
import com.example.moodtracker.data.MoodType
import java.text.SimpleDateFormat
import java.util.*

class MoodDetailsFragment : Fragment() {

    private lateinit var moodIcon: ImageView
    private lateinit var dateText: TextView
    private lateinit var noteText: TextView
    private lateinit var categoryText: TextView
    private lateinit var sleptText: TextView
    private lateinit var activeText: TextView
    private lateinit var ratingText: TextView
    private lateinit var importantText: TextView
    private lateinit var signatureText: TextView
    private lateinit var deleteButton: Button
    private lateinit var shareButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mood_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val moodId = arguments?.getString("moodId") ?: return
        val entry = FakeMoodRepository.getMoodById(UUID.fromString(moodId)) ?: return

        moodIcon = view.findViewById(R.id.detailsMoodIcon)
        dateText = view.findViewById(R.id.detailsDate)
        noteText = view.findViewById(R.id.detailsNote)
        categoryText = view.findViewById(R.id.detailsCategory)
        sleptText = view.findViewById(R.id.detailsSlept)
        activeText = view.findViewById(R.id.detailsActive)
        ratingText = view.findViewById(R.id.detailsRating)
        importantText = view.findViewById(R.id.detailsImportant)
        signatureText = view.findViewById(R.id.detailsSignature)
        deleteButton = view.findViewById(R.id.detailsDeleteButton)
        shareButton = view.findViewById(R.id.detailsShareButton)

        moodIcon.setImageResource(
            when (entry.mood) {
                MoodType.HAPPY -> R.drawable.ic_happy
                MoodType.NEUTRAL -> R.drawable.ic_neutral
                MoodType.SAD -> R.drawable.ic_sad
            }
        )

        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        dateText.text = "Data: ${sdf.format(entry.date)}"
        noteText.text = "Notatka: ${entry.note}"
        categoryText.text = "Kategoria: ${entry.category}"
        sleptText.text = "Spałem dobrze: ${if (entry.sleptWell) "Tak" else "Nie"}"
        activeText.text = "Aktywny: ${if (entry.physicallyActive) "Tak" else "Nie"}"
        ratingText.text = "Ocena: ${entry.rating} ⭐"
        importantText.text = "Ważny dzień: ${if (entry.markedImportant) "Tak" else "Nie"}"
        signatureText.text = "Podpis: ${entry.usernameSignature}"

        deleteButton.setOnClickListener {
            FakeMoodRepository.moodList.remove(entry)
            Toast.makeText(requireContext(), "Wpis usunięty", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        shareButton.setOnClickListener {
            Toast.makeText(requireContext(), "Udostępnianie (TODO)", Toast.LENGTH_SHORT).show()
        }
    }
}
