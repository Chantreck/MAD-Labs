package com.tsu.silentmoon.home_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.silentmoon.R
import com.tsu.silentmoon.choose_topic_activity.TopicRVAdapter
import com.tsu.silentmoon.course_details_activity.CourseDetailsActivity
import com.tsu.silentmoon.databinding.FragmentMeditateBinding
import com.tsu.silentmoon.music_activity.MusicActivity

class MeditateFragment : Fragment(R.layout.fragment_meditate) {
    private lateinit var binding: FragmentMeditateBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentMeditateBinding.bind(view);

        val filterRecyclerView: RecyclerView = binding.filterRecyclerView;
        filterRecyclerView.setHasFixedSize(true);
        filterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            adapter = FilterRVAdapter(context, "light");
        }

        binding.dailyCalmImageView.setOnClickListener {
            val intent = Intent(context, CourseDetailsActivity::class.java);
            startActivity(intent);
        }

        binding.playButton.setOnClickListener {
            val intent = Intent(context, CourseDetailsActivity::class.java);
            startActivity(intent);
        }

        val intent = Intent(context, MusicActivity::class.java)
        val cardsRecyclerView: RecyclerView = binding.cardsRecyclerView;
        cardsRecyclerView.setHasFixedSize(true);
        cardsRecyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = TopicRVAdapter(context, backgrounds, titles, titleColors, intent);
        }
    }

    private val backgrounds = arrayOf(
        R.drawable.days_of_calm,
        R.drawable.anxiety_release,
        R.drawable.unnamed_meditation,
        R.drawable.how_to_meditate
    );
    private val titles = arrayOf(
        R.string.days_of_calm,
        R.string.anxiety_release,
        R.string.empty,
        R.string.how_to_meditate
    );
    private val titleColors = arrayOf(R.color.white, R.color.white, R.color.white, R.color.white);
}