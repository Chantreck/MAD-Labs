package com.tsu.silentmoon.choose_topic_activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.databinding.ItemTopicBinding

class TopicRVAdapter(
    private val context: Context,
    private val backgrounds: Array<Int>,
    private val titles: Array<Int>,
    private val titleColors: Array<Int>,
    private val intent: Intent
) : RecyclerView.Adapter<TopicRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        var background: ImageView = binding.backgroundImageView;
        var title: TextView = binding.titleTextView;

        init {
            itemView.setOnClickListener {
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(group.context), group, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.background.setImageResource(backgrounds[position]);
        holder.title.setText(titles[position]);
        holder.title.setTextColor(ContextCompat.getColor(context, titleColors[position]));
    }

    override fun getItemCount() = backgrounds.size;
}