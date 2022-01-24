package com.tsu.itindr.feed_fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tsu.itindr.R
import com.tsu.itindr.big_profile_activity.BigProfileActivity
import com.tsu.itindr.databinding.FragmentFeedBinding
import com.tsu.itindr.match_activity.MatchActivity
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.setCircleImage
import com.tsu.itindr.model.utils.setDefaultImage
import com.tsu.itindr.model.utils.setSelectedTopics
import com.tsu.itindr.people_fragment.model.User

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private companion object {
        private const val INTENT_TAG = "userId"
        private const val INTENT_USER = "userObject"
    }

    private lateinit var binding: FragmentFeedBinding
    private val viewModel by viewModels<FeedViewModel>()
    private lateinit var user: User

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeedBinding.bind(view)

        binding.profilePictureImage.clipToOutline = true
        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.currentUser.observe(this) { user ->
            binding.cardScroll.isVisible = true
            binding.refuseButton.isVisible = true
            binding.likeButton.isVisible = true
            binding.progressBar.isVisible = false

            if (user.name.isNotBlank()) {
                binding.nameText.text = user.name
            }

            binding.descriptionText.text = user.aboutMyself

            if (user.avatar == null) {
                binding.profilePictureImage.setDefaultImage()
            } else {
                binding.profilePictureImage.setCircleImage(user.avatar)
            }

            if (isAdded && activity != null) {
                binding.interestsChipGroup.setSelectedTopics(layoutInflater, user.topics)
            }

            this.user = user
        }

        viewModel.isPlaceholderShown.observe(this) {
            binding.cardScroll.isVisible = false
            binding.refuseButton.isVisible = false
            binding.likeButton.isVisible = false
            binding.placeholderText.isVisible = true
            binding.progressBar.isVisible = false
        }

        viewModel.isMutual.observe(this) {
            val intent = Intent(context, MatchActivity::class.java)
            intent.putExtra(INTENT_TAG, user.userId)
            startActivity(intent)
        }

        viewModel.errorMessage.observe(this) {
            MessageShower.showToast(context, it)
        }

        viewModel.message.observe(this) {
            MessageShower.showToast(context, it)
        }
    }

    private fun initListeners() {
        binding.profilePictureImage.setOnClickListener {
            val intent = Intent(context, BigProfileActivity::class.java)
            intent.putExtra(INTENT_USER, user)
            startActivity(intent)
        }

        binding.likeButton.setOnClickListener {
            viewModel.likeUser(user)
        }

        binding.refuseButton.setOnClickListener {
            viewModel.dislikeUser(user)
        }
    }
}