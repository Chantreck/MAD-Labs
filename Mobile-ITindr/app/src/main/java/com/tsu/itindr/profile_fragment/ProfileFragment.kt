package com.tsu.itindr.profile_fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tsu.itindr.R
import com.tsu.itindr.databinding.FragmentProfileBinding
import com.tsu.itindr.edit_activity.EditActivity
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.setCircleImage
import com.tsu.itindr.model.utils.setDefaultImage
import com.tsu.itindr.model.utils.setSelectedTopics
import com.tsu.itindr.start_activity.StartActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        viewModel.profile.observe(this) {
            if (it == null) return@observe

            val user = it.toDto().toDomain()

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
        }

        viewModel.error.observe(this) {
            MessageShower.showToast(context, it)
        }

        viewModel.loggedOut.observe(this) {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun initListeners() {
        binding.editButton.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
}