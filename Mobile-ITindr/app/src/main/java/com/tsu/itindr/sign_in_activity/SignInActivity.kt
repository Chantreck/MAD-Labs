package com.tsu.itindr.sign_in_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivitySignInBinding
import com.tsu.itindr.hub_activity.HubActivity
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.hideError
import com.tsu.itindr.model.utils.showError

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {
    private val binding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initObservers()
        initButtonListeners()
        initInputListeners()
    }

    private fun initObservers() {
        viewModel.isEmailIncorrect.observe(this) { errorCode ->
            if (errorCode == 0) {
                binding.emailInput.hideError()
            } else {
                binding.emailInput.showError(errorCode)
            }
        }

        viewModel.isPasswordIncorrect.observe(this) { errorCode ->
            if (errorCode == 0) {
                binding.passwordInput.hideError()
            } else {
                binding.passwordInput.showError(errorCode)
            }
        }

        viewModel.loginResult.observe(this) { message ->
            if (message == null) {
                val intent = Intent(this, HubActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                MessageShower.showToast(this, message)
            }
        }
    }

    private fun initButtonListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.logInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun initInputListeners() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, st: Int, c: Int, aft: Int) {}
            override fun onTextChanged(s: CharSequence, st: Int, bef: Int, aft: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    binding.emailInput.hideError()
                }
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, st: Int, c: Int, aft: Int) {}
            override fun onTextChanged(s: CharSequence, st: Int, bef: Int, aft: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    binding.passwordInput.hideError()
                }
            }
        })
    }
}