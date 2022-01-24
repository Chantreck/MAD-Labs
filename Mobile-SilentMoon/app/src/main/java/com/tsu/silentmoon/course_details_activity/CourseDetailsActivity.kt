package com.tsu.silentmoon.course_details_activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityCourseDetailsBinding

class CourseDetailsActivity : AppCompatActivity(R.layout.activity_course_details) {
    private lateinit var binding: ActivityCourseDetailsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val tabLayout: TabLayout = binding.tabLayout;
        val viewPager: ViewPager2 = binding.viewPager;
        viewPager.adapter = ViewPagerAdapter(this);
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(tabTitles[position]);
        }.attach();

        binding.likeImageView.setOnClickListener {
            Toast.makeText(this@CourseDetailsActivity, getText(R.string.like), Toast.LENGTH_SHORT)
                .show();
        }

        binding.downloadImageView.setOnClickListener {
            Toast.makeText(
                this@CourseDetailsActivity,
                getText(R.string.download),
                Toast.LENGTH_SHORT
            ).show();
        }
    }

    private val tabTitles = arrayOf(R.string.male, R.string.female);
}