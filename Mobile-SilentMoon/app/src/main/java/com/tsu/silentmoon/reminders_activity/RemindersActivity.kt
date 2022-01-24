package com.tsu.silentmoon.reminders_activity

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityRemindersBinding
import com.tsu.silentmoon.home_activity.HomeActivity

class RemindersActivity : AppCompatActivity(R.layout.activity_reminders) {
    private lateinit var binding: ActivityRemindersBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemindersBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.skipTextView.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val itemSize = resources.getDimension(R.dimen.chip_size);
        val margin = resources.getDimension(R.dimen.x_margin);
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels;
        var padding = (screenWidth - 2 * margin - 7 * itemSize) / 6;
        padding = if (padding > 0) padding else 8f;
        binding.dayPickerChipGroup.setChipSpacing(padding.toInt());
    }
}