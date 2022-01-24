package com.tsu.silentmoon.home_activity

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ItemMeditationBinding

class MeditationRVAdapter(
    private val context: Context,
    private val backgrounds: Array<Int>,
    private val titles: Array<Int>,
    private val intent: Intent
) : RecyclerView.Adapter<MeditationRVAdapter.ViewHolder>() {
    var displayMetrics = DisplayMetrics();
    private var screenWidth = 0;

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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val display = group.context.display
            display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            val display = (group.context as HomeActivity).windowManager.defaultDisplay.getMetrics(
                displayMetrics
            );
        }
        screenWidth = displayMetrics.widthPixels;
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemPadding = context.resources.getDimension(R.dimen.card_image_margin);
        val itemWidth = (screenWidth - 2 * itemPadding) / 2;
        val layoutParams = holder.itemView.layoutParams;
        layoutParams.height = layoutParams.height;
        layoutParams.width = itemWidth.toInt();
        holder.itemView.layoutParams = layoutParams;

        holder.background.setImageResource(backgrounds[position]);
        holder.title.setText(titles[position]);
        holder.subtitle.setText(R.string.meditation);

        holder.title.setTextColor(ContextCompat.getColor(context, R.color.dark));
        holder.subtitle.setTextColor(ContextCompat.getColor(context, R.color.gray));
    }

    override fun getItemCount() = backgrounds.size;
}