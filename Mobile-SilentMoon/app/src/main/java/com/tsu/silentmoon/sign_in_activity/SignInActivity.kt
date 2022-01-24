package com.tsu.silentmoon.sign_in_activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivitySignInBinding
import com.tsu.silentmoon.sign_up_activity.SignUpActivity
import com.tsu.silentmoon.welcome_activity.WelcomeActivity

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {
    private lateinit var binding: ActivitySignInBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater);
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

        val spannable = SpannableString(getString(R.string.sign_up_desc));
        val clickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }

        spannable.setSpan(
            clickable,
            spannable.length - 7,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        binding.signUpDescTextView.text = spannable;
        binding.signUpDescTextView.movementMethod = LinkMovementMethod.getInstance();

        binding.emailTextInputLayout.isEndIconVisible = false;

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.emailTextInputLayout.isEndIconVisible = s.isNotEmpty()
            }
        })
    }
}