package com.tsu.itindr.big_profile_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityBigProfileBinding
import com.tsu.itindr.model.utils.setImagePreview
import com.tsu.itindr.model.utils.setSelectedTopics
import com.tsu.itindr.people_fragment.model.User
import com.tsu.itindr.user_profile_activity.UserProfileModelFactory

class BigProfileActivity : AppCompatActivity(R.layout.activity_big_profile) {
    private companion object {
        private const val INTENT_TAG = "userId"
        private const val INTENT_USER = "userObject"
    }

    private val binding by lazy { ActivityBigProfileBinding.inflate(layoutInflater) }
    private lateinit var viewModel: BigProfileViewModel
    private lateinit var user: User
    private var userReceived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var userId = intent.getStringExtra(INTENT_TAG)
        if (userId == null) {
            user = intent.getParcelableExtra(INTENT_USER) ?: return
            userId = user.userId
            setUser(user)
            userReceived = true
        }

        val factory = UserProfileModelFactory(application, userId)
        viewModel = ViewModelProvider(this, factory)[BigProfileViewModel::class.java]

        if (!userReceived) {
            viewModel.getUserFromRepository()
        }

        initObservers()

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initObservers() {
        viewModel.user.observe(this) {
            setUser(it)
        }
    }

    private fun setUser(user: User) {
        if (user.name.isNotBlank()) {
            binding.nameText.text = user.name
        }

        if (user.aboutMyself != null && user.aboutMyself.isNotBlank()) {
            binding.descriptionText.isVisible = true
            binding.descriptionText.text = user.aboutMyself
        }

        if (user.avatar != null) {
            binding.profilePictureImage.setImagePreview(user.avatar)
        }

        binding.interestsChipGroup.setSelectedTopics(layoutInflater, user.topics)
    }
}