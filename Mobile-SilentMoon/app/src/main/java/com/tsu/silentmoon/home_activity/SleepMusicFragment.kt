package com.tsu.silentmoon.home_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.FragmentSleepMusicBinding
import com.tsu.silentmoon.play_option_activity.PlayOptionActivity

class SleepMusicFragment : Fragment(R.layout.fragment_sleep_music) {
    private lateinit var binding: FragmentSleepMusicBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSleepMusicBinding.bind(view);

        val intent = Intent(context, PlayOptionActivity::class.java);
        val recommendations: RecyclerView = binding.cardsRecyclerView;
        recommendations.setHasFixedSize(true);
        recommendations.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
            adapter = SleepMusicRVAdapter(context, backgrounds, titles, intent);
        }

        binding.toolbar.setNavigationOnClickListener {
            activity?.supportFragmentManager?.popBackStack();
        }
    }

    private val backgrounds = arrayOf(
        R.drawable.night_island,
        R.drawable.sweet_sleep,
        R.drawable.good_night,
        R.drawable.moon_clouds,
        R.drawable.night_island,
        R.drawable.sweet_sleep,
        R.drawable.good_night,
        R.drawable.moon_clouds
    )
    private val titles = arrayOf(
        R.string.night_island,
        R.string.sweet_sleep,
        R.string.good_night,
        R.string.moon_clouds,
        R.string.night_island,
        R.string.sweet_sleep,
        R.string.good_night,
        R.string.moon_clouds
    )
}