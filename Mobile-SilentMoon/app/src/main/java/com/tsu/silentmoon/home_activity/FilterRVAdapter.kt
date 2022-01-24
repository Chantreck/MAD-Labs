package com.tsu.silentmoon.home_activity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ItemFilterBinding

class FilterRVAdapter(private val context: Context, private val theme: String) :
    RecyclerView.Adapter<FilterRVAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        var background: ToggleButton = binding.backgroundToggleButton;
        var icon: ImageView = binding.iconImageView;
        var filterName: TextView = binding.filterNameTextView;
    }

    override fun onCreateViewHolder(group: ViewGroup, position: Int): ViewHolder {
        val binding = ItemFilterBinding.inflate(LayoutInflater.from(group.context), group, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.icon.setImageResource(icons[position]);
        holder.filterName.setText(filterNames[position]);

        if (theme == "light") {
            holder.background.setBackgroundResource(R.drawable.filter_toggle_bg);
            holder.filterName.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.filter_light_inactive
                )
            );
        } else {
            holder.background.setBackgroundResource(R.drawable.filter_night_toggle_bg);
            holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.dark_gray));
        }

        if (position == 0) {
            holder.background.isChecked = true;
            if (theme == "light") {
                holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.dark));
            } else {
                holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.night));
            }
        }

        holder.background.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (theme == "light") {
                    holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.dark));
                } else {
                    holder.filterName.setTextColor(ContextCompat.getColor(context, R.color.night));
                }
            } else {
                if (theme == "light") {
                    holder.filterName.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.filter_light_inactive
                        )
                    );
                } else {
                    holder.filterName.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.dark_gray
                        )
                    );
                }
            }
        }
    }

    override fun getItemCount() = icons.size;
    private val filterNames =
        arrayOf(R.string.all, R.string.my, R.string.anxious, R.string.sleep, R.string.kids);
    private val icons = arrayOf(
        R.drawable.ic_all,
        R.drawable.ic_my,
        R.drawable.ic_anxious,
        R.drawable.ic_sleep,
        R.drawable.ic_kids
    );
}