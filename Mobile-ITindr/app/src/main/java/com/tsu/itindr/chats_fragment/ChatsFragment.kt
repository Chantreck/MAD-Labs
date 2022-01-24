package com.tsu.itindr.chats_fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.itindr.R
import com.tsu.itindr.chat_activity.ChatActivity
import com.tsu.itindr.chats_fragment.model.ChatListAdapter
import com.tsu.itindr.databinding.FragmentChatsBinding
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.database.chat.ChatPreview

class ChatsFragment : Fragment(R.layout.fragment_chats) {
    private companion object {
        private const val CHAT_ID = "chatId"
    }

    private lateinit var binding: FragmentChatsBinding
    private val viewModel by viewModels<ChatsViewModel>()
    private var adapter: ChatListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChatsBinding.bind(view)

        setupRecycler()
        initObservers()
    }

    private fun setupRecycler() {
        val context = context

        val chatClickListener = object : ChatListAdapter.ChatClickListener {
            override fun onChatClick(chat: ChatPreview) {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra(CHAT_ID, chat.id)
                startActivity(intent)
            }
        }

        adapter = context?.let { ChatListAdapter(chatClickListener) }

        if (context != null) {
            binding.chatsRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding.chatsRecyclerView.adapter = adapter

            val divider = DividerItemDecoration(context, RecyclerView.VERTICAL)
            val drawable = AppCompatResources.getDrawable(context, R.drawable.shape_chat_divider)
            if (drawable != null) {
                divider.setDrawable(drawable)
            }
            binding.chatsRecyclerView.addItemDecoration(divider)
        }
    }

    private fun initObservers() {
        viewModel.chats.observe(this) {
            if (it.isNotEmpty()) {
                adapter?.submitList(it)
                binding.progressBar.isVisible = false
            }
        }

        viewModel.errors.observe(this) {
            MessageShower.showToast(context, it)
        }
    }
}