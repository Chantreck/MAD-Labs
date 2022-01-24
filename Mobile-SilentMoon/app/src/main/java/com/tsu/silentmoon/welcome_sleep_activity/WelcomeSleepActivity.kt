package com.tsu.silentmoon.welcome_sleep_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityWelcomeSleepBinding

class WelcomeSleepActivity : AppCompatActivity(R.layout.activity_welcome_sleep) {
    private lateinit var binding: ActivityWelcomeSleepBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeSleepBinding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.getStartedButton.setOnClickListener {
            finish();
        }
    }
}