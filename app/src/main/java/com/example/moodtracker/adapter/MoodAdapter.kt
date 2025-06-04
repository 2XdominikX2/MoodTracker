package com.example.moodtracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.R
import com.example.moodtracker.data.MoodEntry
import com.example.moodtracker.data.MoodType
import java.text.SimpleDateFormat
import java.util.*

class MoodAdapter(
    private val moodList: List<MoodEntry>,
    private val onItemClick: (MoodEntry) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood_entry, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val entry = moodList[position]
        holder.bind(entry)
        holder.itemView.setOnClickListener { onItemClick(entry) }
    }

    override fun getItemCount() = moodList.size

    inner class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val moodIcon: ImageView = itemView.findViewById(R.id.moodIcon)
        private val moodDate: TextView = itemView.findViewById(R.id.moodDate)
        private val moodNote: TextView = itemView.findViewById(R.id.moodNote)

        fun bind(entry: MoodEntry) {
            moodIcon.setImageResource(
                when (entry.mood) {
                    MoodType.HAPPY -> R.drawable.ic_happy
                    MoodType.NEUTRAL -> R.drawable.ic_neutral
                    MoodType.SAD -> R.drawable.ic_sad
                }
            )
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            moodDate.text = sdf.format(entry.date)
            moodNote.text = entry.note
        }
    }
}
