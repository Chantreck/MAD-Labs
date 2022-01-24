package com.tsu.silentmoon.course_details_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ItemNarratorBinding
import com.tsu.silentmoon.music_activity.MusicActivity

class NarratorRVAdapter(private val context: Context) :
    RecyclerView.Adapter<NarratorRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemNarratorBinding) : RecyclerView.ViewHolder(binding.root) {
        var playButton: ToggleButton = binding.playButton;
        var title: TextView = binding.titleTextView;
        var duration: TextView = binding.durationTextView;

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, MusicActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
        val binding = ItemNarratorBinding.inflate(LayoutInflater.from(group.context), group, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(titles[position]);
        holder.duration.setText(durations[position]);

        when (states[position]) {
            "accent" -> {
                holder.playButton.setBackgroundResource(R.drawable.btn_play_narrator_accent_toggle)
            }
            "regular" -> {
                holder.playButton.setBackgroundResource(R.drawable.btn_play_narrator_toggle)
            }
        }
    }

    override fun getItemCount() = titles.size;
    private val titles =
        arrayOf(R.string.focus_attention, R.string.body_scan, R.string.making_happiness);
    private val durations =
        arrayOf(R.string.long_duration, R.string.medium_duration, R.string.short_duration);
    private val states = arrayOf("accent", "regular", "regular");
}