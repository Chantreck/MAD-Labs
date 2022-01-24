package com.tsu.silentmoon.choose_topic_activity

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.TextAppearanceSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityChooseTopicBinding
import com.tsu.silentmoon.reminders_activity.RemindersActivity

class ChooseTopicActivity : AppCompatActivity(R.layout.activity_choose_topic) {
    private lateinit var binding: ActivityChooseTopicBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseTopicBinding.inflate(layoutInflater);
        setContentView(binding.root);

        val intent = Intent(this, RemindersActivity::class.java)
        val recyclerView: RecyclerView = binding.topicsRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = TopicRVAdapter(context, backgrounds, titles, titleColors, intent);
        }

        val spannable = SpannableStringBuilder(getString(R.string.choose_topic_title));
        spannable.setSpan(
            TextAppearanceSpan(this, R.style.bold_left_header),
            0,
            15,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        binding.titleTextView.text = spannable;
    }

    private val backgrounds = arrayOf(
        R.drawable.reduce_stress,
        R.drawable.improve_perfomance,
        R.drawable.reduce_anxiety,
        R.drawable.increase_happiness,
        R.drawable.personal_growth,
        R.drawable.better_sleep,
        R.drawable.unnamed_topic_2,
        R.drawable.unnamed_topic_1
    )

    private val titles = arrayOf(
        R.string.reduce_stress,
        R.string.improve_performance,
        R.string.reduce_anxiety,
        R.string.increase_happiness,
        R.string.personal_growth,
        R.string.better_sleep,
        R.string.empty,
        R.string.empty
    )

    private val titleColors = arrayOf(
        R.color.light,
        R.color.light,
        R.color.dark,
        R.color.dark,
        R.color.light,
        R.color.light,
        R.color.light,
        R.color.light
    )
}