package com.tsu.itindr.image_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityImageBinding
import com.tsu.itindr.model.utils.setImagePreview

class ImageActivity : AppCompatActivity(R.layout.activity_image) {
    private companion object {
        private const val IMAGE = "image"
    }

    private val binding by lazy {
        ActivityImageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageUri = intent.getStringExtra(IMAGE)
        if (imageUri == null) {
            finish()
        } else {
            binding.image.setImagePreview(imageUri)
        }
    }
}