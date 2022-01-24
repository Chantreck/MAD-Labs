package com.tsu.itindr.chat_activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.View.GONE
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.itindr.R
import com.tsu.itindr.chat_activity.model.*
import com.tsu.itindr.databinding.ActivityChatBinding
import com.tsu.itindr.image_activity.ImageActivity
import com.tsu.itindr.model.utils.ImageUtils
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.setCircleImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity(R.layout.activity_chat) {
    private companion object {
        private const val CHAT_ID = "chatId"
        private const val IMAGE = "image"
    }

    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    private lateinit var factory: ChatViewModelFactory
    private lateinit var viewModel: ChatViewModel
    private var image: Uri? = null
    private lateinit var id: String

    private val imageClickListener = object : ChatAdapter.ImageClickListener {
        override fun onImageClickListener(image: String) {
            val intent = Intent(this@ChatActivity, ImageActivity::class.java)
            intent.putExtra(IMAGE, image)
            startActivity(intent)
        }
    }
    private val adapter = ChatAdapter(imageClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        id = intent.getStringExtra(CHAT_ID) ?: return
        factory = ChatViewModelFactory(application, id)
        viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        initRecyclerView()
        initObservers()
        initListeners()
        viewModel.getChatInfo()
    }

    private fun initRecyclerView() {
        binding.messagesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        binding.messagesRecyclerView.adapter = adapter
        binding.messagesRecyclerView.addItemDecoration(ChatMarginDecorator())
    }

    private fun initObservers() {
        lifecycleScope.launch {
            viewModel.pager.flow.collectLatest { pagingData ->
                adapter.submitData(pagingData.map { it.toDomain() })
            }
        }

        viewModel.chat.observe(this) {
            binding.toolbarNameText.text = it.title
            binding.toolbarProfileImage.setCircleImage(it.avatar)
            binding.toolbarProfileImage.clipToOutline = true
        }

        viewModel.errors.observe(this) {
            MessageShower.showToast(this, it)
        }
    }

    private fun initListeners() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.cameraButton.setOnClickListener {
            ImageUtils.getPicture(this, checkCameraPermission, chooseImageContract)
        }

        binding.removeAttachmentButton.setOnClickListener {
            image = null
            binding.attachmentImage.visibility = GONE
            binding.removeAttachmentButton.visibility = GONE
        }

        binding.sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        val inputText: String = binding.messageEditText.text.toString()
        val imagePart = image?.let { ImageUtils.getImageBody(this, it) }
        viewModel.sendMessage(inputText, imagePart)
    }

    private fun takePicture() {
        image = ImageUtils.getTempFileUri(this)
        takePictureContract.launch(image)
    }

    private fun setPicture(uri: Uri) {
        image = uri
        binding.attachmentImage.isVisible = true
        binding.removeAttachmentButton.isVisible = true
        binding.attachmentImage.setImageBitmap(ImageUtils.getImageBitmap(this, uri))
        binding.attachmentImage.clipToOutline = true
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