package com.tsu.silentmoon.home_activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.course_details_activity.CourseDetailsActivity
import com.tsu.silentmoon.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);

        val recyclerView: RecyclerView = binding.courseRecyclerView;
        val intent = Intent(context, CourseDetailsActivity::class.java);
        recyclerView.setHasFixedSize(true);
        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2);
            adapter = CourseRVAdapter(context, intent);
        }

        binding.dailyThoughtsImageView.setOnClickListener {
            startActivity(intent)
        }

        binding.playButton.setOnClickListener {
            startActivity(intent)
        }

        val recommendations: RecyclerView = binding.recommendationsRecyclerView;
        val snapHelper = PagerSnapHelper();
        snapHelper.attachToRecyclerView(recommendations);
        recommendations.setHasFixedSize(true);
        recommendations.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false);
            adapter = MeditationRVAdapter(context, backgrounds, titles, intent);
        }

        val username = " " + getString(R.string.username);
        val spannable = SpannableStringBuilder(getString(R.string.home_title));
        spannable.insert(13, username);
        binding.titleTextView.text = spannable;
    }

    private val backgrounds = arrayOf(R.drawable.focus_meditation, R.drawable.happiness_meditation);
    private val titles = arrayOf(R.string.focus, R.string.happiness);
}