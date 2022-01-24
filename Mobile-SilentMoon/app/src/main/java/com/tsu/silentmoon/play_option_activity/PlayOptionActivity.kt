package com.tsu.silentmoon.play_option_activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityPlayOptionBinding
import com.tsu.silentmoon.home_activity.SleepMusicRVAdapter
import com.tsu.silentmoon.night_music_activity.NightMusicActivity

class PlayOptionActivity : AppCompatActivity(R.layout.activity_play_option) {
    private lateinit var binding: ActivityPlayOptionBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayOptionBinding.inflate(layoutInflater);
        setContentView(binding.root)

        val recyclerIntent = Intent(this, PlayOptionActivity::class.java);
        val recommendations: RecyclerView = binding.relatedRecyclerView;
        recommendations.setHasFixedSize(true);
        recommendations.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
            adapter = SleepMusicRVAdapter(context, backgrounds, titles, recyclerIntent);
        }

        binding.likeImageView.setOnClickListener {
            Toast.makeText(this@PlayOptionActivity, getText(R.string.like), Toast.LENGTH_SHORT)
                .show();
        }

        binding.downloadImageView.setOnClickListener {
            Toast.makeText(this@PlayOptionActivity, getText(R.string.download), Toast.LENGTH_SHORT)
                .show();
        }

        binding.controlsToolbar.setNavigationOnClickListener {
            finish();
        }

        binding.playButton.setOnClickListener {
            val playIntent = Intent(this, NightMusicActivity::class.java);
            startActivity(playIntent);
        }
    }

    private val backgrounds = arrayOf(R.drawable.moon_clouds, R.drawable.sweet_sleep);
    private val titles = arrayOf(R.string.moon_clouds, R.string.sweet_sleep);
}