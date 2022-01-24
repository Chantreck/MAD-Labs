package com.tsu.itindr.user_profile_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.tsu.itindr.R
import com.tsu.itindr.big_profile_activity.BigProfileActivity
import com.tsu.itindr.databinding.ActivityUserProfileBinding
import com.tsu.itindr.match_activity.MatchActivity
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.setCircleImage
import com.tsu.itindr.model.utils.setDefaultImage
import com.tsu.itindr.model.utils.setSelectedTopics
import com.tsu.itindr.people_fragment.model.User

class UserProfileActivity : AppCompatActivity(R.layout.activity_user_profile) {
    private companion object {
        private const val INTENT_TAG = "userId"
    }

    private val binding by lazy { ActivityUserProfileBinding.inflate(layoutInflater) }
    private lateinit var viewModel: UserProfileViewModel
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userId = intent.getStringExtra(INTENT_TAG) ?: return
        val factory = UserProfileModelFactory(application, userId)
        viewModel = ViewModelProvider(this, factory)[UserProfileViewModel::class.java]

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.profilePictureImage.setOnClickListener {
            val intent = Intent(this, BigProfileActivity::class.java)
            intent.putExtra(INTENT_TAG, userId)
            startActivity(intent)
        }

        binding.likeButton.setOnClickListener {
            viewModel.likeUser()
        }

        binding.refuseButton.setOnClickListener {
            viewModel.dislikeUser()
        }
    }

    private fun initObservers() {
        viewModel.isMutual.observe(this) {
            val intent = Intent(this, MatchActivity::class.java)
            intent.putExtra(INTENT_TAG, userId)
            startActivity(intent)
        }

        viewModel.errorMessage.observe(this) {
            MessageShower.showToast(this, it)
        }

        viewModel.message.observe(this) {
            MessageShower.showToast(this, it)
        }

        viewModel.user.observe(this) {
            setProfile(it)
        }
    }

    private fun setProfile(user: User) {
        if (user.name.isNotBlank()) {
            binding.nameText.text = user.name
        }

        if (user.avatar == null) {
            binding.profilePictureImage.setDefaultImage()
        } else {
            binding.profilePictureImage.setCircleImage(user.avatar)
        }

        binding.descriptionText.text = user.aboutMyself
        binding.interestsChipGroup.setSelectedTopics(layoutInflater, user.topics)
    }
}