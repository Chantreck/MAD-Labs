package com.tsu.itindr.match_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.R
import com.tsu.itindr.chat_activity.ChatActivity
import com.tsu.itindr.databinding.ActivityMatchBinding
import com.tsu.itindr.model.utils.MessageShower

class MatchActivity : AppCompatActivity(R.layout.activity_match) {
    companion object {
        private const val INTENT_TAG = "userId"
        private const val CHAT_ID = "chatId"
    }

    private val binding by lazy { ActivityMatchBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MatchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userId = intent.getStringExtra(INTENT_TAG) ?: ""

        initObservers()

        binding.writeMessageButton.setOnClickListener {
            viewModel.createChat(userId)
        }
    }

    private fun initObservers() {
        viewModel.error.observe(this) {
            MessageShower.showToast(this, it)
        }

        viewModel.chatId.observe(this) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra(CHAT_ID, it)
            startActivity(intent)
            finish()
        }
    }
}