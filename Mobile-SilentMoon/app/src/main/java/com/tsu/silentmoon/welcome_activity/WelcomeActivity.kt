package com.tsu.silentmoon.welcome_activity

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.choose_topic_activity.ChooseTopicActivity
import com.tsu.silentmoon.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity(R.layout.activity_welcome) {
    private lateinit var binding: ActivityWelcomeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.getStartedButton.setOnClickListener {
            val intent = Intent(this, ChooseTopicActivity::class.java);
            startActivity(intent);
        }

        val username = getString(R.string.username);
        val spannable = SpannableStringBuilder(getString(R.string.welcome));
        spannable.setSpan(
            TextAppearanceSpan(this, R.style.bold_light_header),
            0,
            12,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        );
        spannable.insert(3, username);
        binding.titleTextView.text = spannable;
    }
}