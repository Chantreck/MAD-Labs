package com.tsu.itindr.sign_up_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tsu.itindr.R
import com.tsu.itindr.databinding.ActivitySignUpBinding
import com.tsu.itindr.first_setup_activity.FirstSetupActivity
import com.tsu.itindr.model.utils.MessageShower
import com.tsu.itindr.model.utils.hideError
import com.tsu.itindr.model.utils.showError

class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {
    private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<SignUpViewModel>()

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

        viewModel.isRepeatPasswordIncorrect.observe(this) { errorCode ->
            if (errorCode == 0) {
                binding.repeatPasswordInput.hideError()
            } else {
                binding.repeatPasswordInput.showError(errorCode)
            }
        }

        viewModel.registerResult.observe(this) { message ->
            if (message == null) {
                val intent = Intent(this, FirstSetupActivity::class.java)
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

        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val repeatPassword = binding.repeatPasswordEditText.text.toString()
            viewModel.register(email, password, repeatPassword)
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

        binding.repeatPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, st: Int, c: Int, aft: Int) {}
            override fun onTextChanged(s: CharSequence, st: Int, bef: Int, aft: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    binding.repeatPasswordInput.hideError()
                }
            }
        })
    }
}