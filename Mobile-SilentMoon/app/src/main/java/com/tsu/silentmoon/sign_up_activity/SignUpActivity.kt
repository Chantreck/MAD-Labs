package com.tsu.silentmoon.sign_up_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivitySignUpBinding
import com.tsu.silentmoon.welcome_activity.WelcomeActivity

class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {
    private lateinit var binding: ActivitySignUpBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.facebookButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java);
            startActivity(intent);
        }

        binding.googleButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java);
            startActivity(intent);
        }

        binding.logInButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java);
            startActivity(intent);
        }

        val spannable = SpannableString(getString(R.string.policy_check));
        val clickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@SignUpActivity, getText(R.string.policy), Toast.LENGTH_SHORT)
                    .show();
            }
        }

        spannable.setSpan(
            clickable,
            spannable.length - 14,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        binding.privacyCheckBox.text = spannable;
        binding.privacyCheckBox.movementMethod = LinkMovementMethod.getInstance();

        binding.nameTextInputLayout.isEndIconVisible = false;
        binding.emailTextInputLayout.isEndIconVisible = false;

        binding.nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.nameTextInputLayout.isEndIconVisible = s.isNotEmpty()
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.emailTextInputLayout.isEndIconVisible = s.isNotEmpty()
            }
        })
    }
}