package com.tsu.silentmoon.home_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.course_details_activity.CourseDetailsActivity
import com.tsu.silentmoon.databinding.ItemCourseBinding

class CourseRVAdapter(private val context: Context, private val intent: Intent) :
    RecyclerView.Adapter<CourseRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root) {
        var background: ImageView = binding.backgroundImageView;
        var title: TextView = binding.titleTextView;
        var subtitle: TextView = binding.subtitleTextView;
        var duration: TextView = binding.durationTextView;
        var button: Button = binding.startButton;

        init {
            itemView.setOnClickListener {
                val intent = Intent(context, CourseDetailsActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
        val binding = ItemCourseBinding.inflate(LayoutInflater.from(group.context), group, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.background.setImageResource(backgrounds[position]);
        holder.title.setText(titles[position]);
        holder.subtitle.setText(subtitles[position]);
        holder.duration.setText(R.string.duration);

        when (themes[position]) {
            "light" -> {
                holder.title.setTextColor(ContextCompat.getColor(context, R.color.light));
                holder.subtitle.setTextColor(ContextCompat.getColor(context, R.color.light));
                holder.duration.setTextColor(ContextCompat.getColor(context, R.color.light));
                holder.button.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.light_button
                    )
                );
                holder.button.setTextColor(ContextCompat.getColor(context, R.color.dark));
            }
            "dark" -> {
                holder.title.setTextColor(ContextCompat.getColor(context, R.color.dark));
                holder.subtitle.setTextColor(ContextCompat.getColor(context, R.color.dark));
                holder.duration.setTextColor(ContextCompat.getColor(context, R.color.dark));
                holder.button.setBackgroundColor(ContextCompat.getColor(context, R.color.dark));
                holder.button.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
            }
        }

        holder.button.setOnClickListener {
            context.startActivity(intent);
        }
    }

    override fun getItemCount() = backgrounds.size;
    private val backgrounds = arrayOf(R.drawable.basics_course, R.drawable.relaxation_music);
    private val titles = arrayOf(R.string.basics, R.string.relaxation);
    private val subtitles = arrayOf(R.string.course, R.string.music);
    private val themes = arrayOf("light", "dark");
}