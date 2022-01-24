package com.tsu.itindr.first_setup_activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityFirstSetupBinding
import com.tsu.itindr.hub_activity.HubActivity
import com.tsu.itindr.model.utils.*
import okhttp3.MultipartBody

class FirstSetupActivity : AppCompatActivity(R.layout.activity_first_setup) {
    private val binding by lazy { ActivityFirstSetupBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FirstSetupViewModel>()
    private var image: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profilePictureImage.clipToOutline = true

        initObservers()

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val about = binding.aboutEditText.text.toString()
            val topics = binding.interestsChipGroup.getSelectedTopics()
            viewModel.saveProfileInfo(name, about, topics)
        }
    }

    private fun initObservers() {
        viewModel.profile.observe(this) { user ->
            if (user != null) {
                (binding.nameEditText as TextView).text = user.name
                (binding.aboutEditText as TextView).text = user.aboutMyself

                if (user.avatar.isNullOrEmpty()) {
                    setUploadImageListener()
                    binding.imageProgressBar.isVisible = false
                } else {
                    setDeleteImageListener()
                    binding.profilePictureImage.setCircleImage(user.avatar)
                    binding.imageProgressBar.isVisible = false
                }

                binding.progressBar.isVisible = false
            }
        }

        viewModel.topics.observe(this) {
            binding.interestsChipGroup.setTopics(layoutInflater, it)
        }

        viewModel.isNameCorrect.observe(this) { errorCode ->
            if (errorCode == 0) {
                binding.nameInput.hideError()
            } else {
                binding.nameInput.showError(errorCode)
            }
        }

        viewModel.error.observe(this) {
            MessageShower.showToast(this, it)
        }

        viewModel.changesSaved.observe(this) {
            if (it) {
                val intent = Intent(this, HubActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private val setUploadImageListener = {
        binding.profilePictureImage.setImageResource(R.drawable.ic_default_profile)
        binding.changePictureButton.setText(R.string.choose_picture)
        binding.changePictureButton.setOnClickListener {
            ImageUtils.getPicture(this, checkCameraPermission, chooseImageContract)
        }
    }

    private val setDeleteImageListener = {
        binding.changePictureButton.setText(R.string.delete_picture)
        binding.changePictureButton.setOnClickListener {
            binding.imageProgressBar.isVisible = true
            viewModel.deleteImage()
        }
    }

    private fun takePicture() {
        image = ImageUtils.getTempFileUri(this)
        takePictureContract.launch(image)
    }

    private val takePictureContract =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { successful ->
            if (successful) {
                image?.let { setPicture(it) }
            } else {
                MessageShower.showToast(this, R.string.image_upload_error)
            }
        }

    private val chooseImageContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            try {
                if (uri != null) {
                    setPicture(uri)
                }
            } catch (e: Exception) {
                MessageShower.showToast(this, R.string.image_upload_error)
            }
        }

    private fun setPicture(uri: Uri) {
        binding.progressBar.isVisible = true
        image = uri

        val requestFile = ImageUtils.getImageBody(this, uri)
        val body = MultipartBody.Part.createFormData("avatar", "avatar.jpg", requestFile)

        viewModel.uploadImage(body)
    }

    private val checkCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    takePicture()
                }
                !shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                    MessageShower.showToast(this, R.string.allow_camera_message)
                }
                else -> {
                    MessageShower.showToast(this, R.string.camera_not_allowed)
                }
            }
        }
}