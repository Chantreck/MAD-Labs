package com.tsu.silentmoon.home_activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityHomeBinding
import com.tsu.silentmoon.music_activity.MusicActivity
import com.tsu.silentmoon.night_music_activity.NightMusicActivity
import com.tsu.silentmoon.welcome_sleep_activity.WelcomeSleepActivity

class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    private lateinit var binding: ActivityHomeBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater);
        setContentView(binding.root);

        if (savedInstanceState == null) {
            openFragment(R.id.menu_home);
        }

        binding.menuBottomNavigation.setOnNavigationItemSelectedListener {
            openFragment(it.itemId);
        }
    }

    private fun openFragment(itemId: Int): Boolean {
        var fragment: Fragment? = null;
        var intent: Intent? = null;

        when (itemId) {
            R.id.menu_home -> {
                fragment = HomeFragment();
                recolor("light");
            }
            R.id.menu_meditate -> {
                fragment = MeditateFragment();
                recolor("light");
            }
            R.id.menu_music -> {
                intent = if (theme == "light") Intent(this, MusicActivity::class.java) else Intent(
                    this,
                    NightMusicActivity::class.java
                );
            };
            R.id.menu_sleep -> {
                fragment = SleepFragment(binding);
                intent = Intent(this, WelcomeSleepActivity::class.java)
                recolor("night");
            };
        }

        if (intent != null) {
            startActivity(intent);
        }

        fragment?.let {
            supportFragmentManager.commit {
                replace(binding.fragmentContainer.id, fragment, fragment.tag);
            }
            return true;
        }
        return false;
    }

    private fun recolor(newTheme: String) {
        if (newTheme != theme) {
            when (newTheme) {
                "light" -> {
                    binding.menuBottomNavigation.setBackgroundResource(R.color.white);
                    val colorState =
                        ContextCompat.getColorStateList(this, R.color.selector_bottom_nav);
                    binding.menuBottomNavigation.itemIconTintList = colorState;
                    binding.menuBottomNavigation.itemTextColor = colorState;
                }
                "night" -> {
                    binding.menuBottomNavigation.setBackgroundResource(R.color.night_bg);
                    val colorState =
                        ContextCompat.getColorStateList(this, R.color.selector_bottom_nav_night);
                    binding.menuBottomNavigation.itemIconTintList = colorState;
                    binding.menuBottomNavigation.itemTextColor = colorState;
                }
            }
            theme = newTheme;
        }
    }

    private var theme = "light";
}