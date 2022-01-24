package com.tsu.itindr.hub_activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.tsu.itindr.R
import com.tsu.itindr.chats_fragment.ChatsFragment
import com.tsu.itindr.databinding.ActivityHubBinding
import com.tsu.itindr.feed_fragment.FeedFragment
import com.tsu.itindr.people_fragment.PeopleFragment
import com.tsu.itindr.profile_fragment.ProfileFragment

class HubActivity : AppCompatActivity(R.layout.activity_hub) {
    private val binding by lazy { ActivityHubBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<HubViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.error.observe(this) {
            if (it) {
                finish()
            }
        }

        if (savedInstanceState == null) {
            openFragment(R.id.feed)
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            openFragment(it.itemId)
        }
    }

    private fun openFragment(itemId: Int): Boolean {
        var fragment: Fragment? = null

        when (itemId) {
            R.id.feed -> {
                fragment = FeedFragment()
            }
            R.id.people -> {
                fragment = PeopleFragment()
            }
            R.id.chats -> {
                fragment = ChatsFragment()
            }
            R.id.profile -> {
                fragment = ProfileFragment()
            }
        }

        fragment?.let {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(binding.fragmentContainer.id, fragment)
            }
            return true
        }
        return false
    }
}