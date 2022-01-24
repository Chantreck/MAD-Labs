package com.tsu.itindr.people_fragment.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemPersonBinding
import com.tsu.itindr.model.utils.setCircleImage

class PeopleAdapter(private val personClickListener: PersonClickListener) :
    PagingDataAdapter<User, PeopleAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemPersonBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                val item = getItem(layoutPosition)
                if (item != null) {
                    personClickListener.onPersonClick(item)
                }
            }
        }

        fun bind(user: User) = with(binding) {
            if (user.avatar != null) {
                personImage.setCircleImage(user.avatar)
            }
            if (user.name.isNotBlank()) {
                personNameText.text = user.name
                personNameText.setTextAppearance(R.style.PersonName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_person, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    interface PersonClickListener {
        fun onPersonClick(user: User)
    }
}