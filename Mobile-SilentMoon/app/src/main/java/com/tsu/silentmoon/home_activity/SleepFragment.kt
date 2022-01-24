package com.tsu.silentmoon.home_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityHomeBinding
import com.tsu.silentmoon.databinding.FragmentSleepBinding
import com.tsu.silentmoon.play_option_activity.PlayOptionActivity

class SleepFragment(private val activityBinding: ActivityHomeBinding) :
    Fragment(R.layout.fragment_sleep) {
    private lateinit var binding: FragmentSleepBinding;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSleepBinding.bind(view);

        val filterRecyclerView: RecyclerView = binding.filterRecyclerView;
        filterRecyclerView.setHasFixedSize(true);
        filterRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            adapter = FilterRVAdapter(context, "night");
        }

        val playOptionIntent = Intent(context, PlayOptionActivity::class.java);
        val recommendations: RecyclerView = binding.cardsRecyclerView;
        recommendations.setHasFixedSize(true);
        recommendations.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false);
            adapter = SleepMusicRVAdapter(context, backgrounds, titles, playOptionIntent);
        }

        binding.oceanMoonImageView.setOnClickListener {
            showSleepMusic();
        }
        binding.startButton.setOnClickListener {
            showSleepMusic();
        }
    }

    private fun showSleepMusic() {
        val newFragment: Fragment = SleepMusicFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(activityBinding.fragmentContainer.id, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private val backgrounds = arrayOf(
        R.drawable.night_island,
        R.drawable.sweet_sleep,
        R.drawable.good_night,
        R.drawable.moon_clouds
    )
    private val titles = arrayOf(
        com.tsu.silentmoon.R.string.night_island,
        com.tsu.silentmoon.R.string.sweet_sleep,
        com.tsu.silentmoon.R.string.good_night,
        com.tsu.silentmoon.R.string.moon_clouds
    )
}