package com.tsu.silentmoon.night_music_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityNightMusicBinding
import java.util.concurrent.TimeUnit

class NightMusicActivity : AppCompatActivity(R.layout.activity_night_music) {
    private lateinit var binding: ActivityNightMusicBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNightMusicBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.controlsToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.likeImageView.setOnClickListener {
            Toast.makeText(this@NightMusicActivity, getText(R.string.like), Toast.LENGTH_SHORT)
                .show();
        }

        binding.downloadImageView.setOnClickListener {
            Toast.makeText(this@NightMusicActivity, getText(R.string.download), Toast.LENGTH_SHORT)
                .show();
        }

        binding.trackSlider.addOnChangeListener { _, value, _ ->
            run {
                binding.currentTimeTextView.text = getCurrentTime(value);
            }
        }

        binding.trackTextView.isSelected = true;
    }

    private fun getCurrentTime(value: Float): String {
        val current = (45 * 60 * value).toLong();
        return String.format(
            "%02d:%02d",
            TimeUnit.SECONDS.toMinutes(current),
            current - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(current))
        );
    }
}