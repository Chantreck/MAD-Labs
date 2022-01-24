package com.tsu.silentmoon.home_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ItemMeditationBinding

class SleepMusicRVAdapter(
    private val context: Context,
    private val backgrounds: Array<Int>,
    private val titles: Array<Int>,
    private val intent: Intent
) : RecyclerView.Adapter<SleepMusicRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemMeditationBinding) : RecyclerView.ViewHolder(binding.root) {
        var background: ImageView = binding.backgroundImageView;
        var title: TextView = binding.titleTextView;
        var subtitle: TextView = binding.subtitleTextView;

        init {
            itemView.setOnClickListener {
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
        val binding =
            ItemMeditationBinding.inflate(LayoutInflater.from(group.context), group, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.background.setImageResource(backgrounds[position]);
        holder.title.setText(titles[position]);
        holder.subtitle.setText(R.string.sleep_music_duration);
        holder.title.setTextColor(ContextCompat.getColor(context, R.color.night));
        holder.subtitle.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
    }

    override fun getItemCount() = backgrounds.size;
}