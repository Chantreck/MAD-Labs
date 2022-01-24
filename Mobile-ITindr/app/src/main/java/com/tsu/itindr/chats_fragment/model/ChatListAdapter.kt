package com.tsu.itindr.chats_fragment.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ItemChatPreviewBinding
import com.tsu.itindr.model.database.chat.ChatPreview
import com.tsu.itindr.model.utils.setCircleImage
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ChatListAdapter(private val chatClickListener: ChatClickListener) :
    ListAdapter<ChatPreview, ChatListAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ChatPreview>() {
            override fun areItemsTheSame(oldItem: ChatPreview, newItem: ChatPreview) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ChatPreview, newItem: ChatPreview) =
                oldItem == newItem
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemChatPreviewBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                chatClickListener.onChatClick(getItem(layoutPosition))
            }
        }

        fun bind(chat: ChatPreview) = with(binding) {
            chatNameText.text = chat.title
            chatProfileImage.setCircleImage(chat.avatar)
            chatProfileImage.clipToOutline = true

            if (chat.lastMessage?.isBlank() == true && chat.attachments) {
                chatMessageText.isVisible = true
                chatMessageText.setText(R.string.image)
                chatMessageText.setTextAppearance(R.style.Chat_Preview_Attachment)
                chatDateText.isVisible = true
                chatDateText.text = getFormattedDate(chat.lastMessageDate)
            }

            if (chat.lastMessage?.isNotBlank() == true) {
                chatMessageText.isVisible = true
                chatMessageText.text = chat.lastMessage
                chatDateText.isVisible = true
                chatDateText.text = getFormattedDate(chat.lastMessageDate)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_chat_preview, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface ChatClickListener {
        fun onChatClick(chat: ChatPreview)
    }

    private fun getFormattedDate(date: String?): String {
        val datetime = ZonedDateTime.parse(date).withZoneSameInstant(ZoneId.systemDefault())
        val now = ZonedDateTime.now().withZoneSameInstant(ZoneId.systemDefault())

        val time: String
        if (DateTimeFormatter.ofPattern("w").format(datetime) == DateTimeFormatter.ofPattern("w").format(now)) {
            if (DateTimeFormatter.ofPattern("D").format(datetime) == DateTimeFormatter.ofPattern("D").format(now)) {
                time = DateTimeFormatter.ofPattern("HH:mm").format(datetime)
            } else {
                time = DateTimeFormatter.ofPattern("EE").format(datetime)
            }
        } else {
            time = DateTimeFormatter.ofPattern("dd MMM").format(datetime)
        }

        return time
    }
}