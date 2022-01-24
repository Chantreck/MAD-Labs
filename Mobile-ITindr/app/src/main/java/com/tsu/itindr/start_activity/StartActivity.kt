package com.tsu.itindr.start_activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivityStartBinding
import com.tsu.itindr.first_setup_activity.FirstSetupActivity
import com.tsu.itindr.first_setup_activity.FirstSetupViewModel
import com.tsu.itindr.hub_activity.HubActivity
import com.tsu.itindr.sign_in_activity.SignInActivity
import com.tsu.itindr.sign_up_activity.SignUpActivity

class StartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<StartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isTokenValid.observe(this) {
            if (it) {
                val intent = Intent(this, HubActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                initView()
            }
        }
    }

    private fun initView() {
        setTheme(R.style.Theme_ITindr_StartScreen)
        setContentView(binding.root)

        binding.logInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}