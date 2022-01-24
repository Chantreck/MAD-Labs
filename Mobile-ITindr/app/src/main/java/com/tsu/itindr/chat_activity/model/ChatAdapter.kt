package com.tsu.itindr.chat_activity.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemMessageInBinding
import com.tsu.itindr.databinding.ItemMessageOutBinding
import com.tsu.itindr.model.database.messages.Message
import com.tsu.itindr.model.utils.SharedPreferencesUtils
import com.tsu.itindr.model.utils.setCircleImage
import com.tsu.itindr.model.utils.setImagePreview
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatAdapter(private val imageClickListener: ImageClickListener) :
    PagingDataAdapter<Message, RecyclerView.ViewHolder>(DIFF) {
    private val userId = SharedPreferencesUtils.getUserId()

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Message, newItem: Message) = oldItem == newItem
        }
        const val MESSAGE_TYPE_IN = R.layout.item_message_in
        const val MESSAGE_TYPE_OUT = R.layout.item_message_out
    }

    inner class MessageInViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMessageInBinding.bind(view)

        init {
            binding.image.setOnClickListener {
                val message = getItem(layoutPosition) ?: return@setOnClickListener
                val image = message.attachments ?: return@setOnClickListener
                imageClickListener.onImageClickListener(image)
            }
        }

        fun bind(message: Message) = with(binding) {
            if (message.text.isNullOrBlank()) {
                messageText.visibility = TextView.GONE
            } else {
                messageText.text = message.text
            }

            dateText.text = getFormattedDate(message.createdAt)

            if (!message.attachments.isNullOrEmpty()) {
                image.visibility = ImageView.VISIBLE
                image.setImagePreview(message.attachments)
                image.clipToOutline = true
            }

            senderImage.setCircleImage(message.senderAvatar)
            senderImage.clipToOutline = true
        }
    }

    inner class MessageOutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMessageOutBinding.bind(view)

        init {
            binding.image.setOnClickListener {
                val message = getItem(layoutPosition) ?: return@setOnClickListener
                val image = message.attachments ?: return@setOnClickListener
                imageClickListener.onImageClickListener(image)
            }
        }

        fun bind(message: Message) = with(binding) {
            if (message.text.isNullOrBlank()) {
                messageText.visibility = TextView.GONE
            } else {
                messageText.text = message.text
            }

            dateText.text = getFormattedDate(message.createdAt)

            if (!message.attachments.isNullOrEmpty()) {
                image.visibility = ImageView.VISIBLE
                image.setImagePreview(message.attachments)
                image.clipToOutline = true
            }

            senderImage.setCircleImage(message.senderAvatar)
            senderImage.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            MESSAGE_TYPE_IN -> MessageInViewHolder(view)
            else -> MessageOutViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (message != null) {
            when (holder) {
                is MessageInViewHolder -> holder.bind(message)
                is MessageOutViewHolder -> holder.bind(message)
                else -> throw Exception()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position) ?: return 0
        return if (item.senderId == userId) {
            MESSAGE_TYPE_OUT
        } else {
            MESSAGE_TYPE_IN
        }
    }

    private fun getFormattedDate(date: String): String {
        val datetime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.systemDefault())

        val dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy").format(datetime)
        val timeFormat = DateTimeFormatter.ofPattern("HH:mm").format(datetime)

        return "$timeFormat • $dateFormat"
    }

    interface ImageClickListener {
        fun onImageClickListener(image: String)
    }
}