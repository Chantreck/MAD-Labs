package com.tsu.silentmoon.main_activity

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tsu.silentmoon.R
import com.tsu.silentmoon.databinding.ActivityMainBinding
import com.tsu.silentmoon.sign_in_activity.SignInActivity
import com.tsu.silentmoon.sign_up_activity.SignUpActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        val spannable = SpannableString(getString(R.string.main_have_account));
        val clickable = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        spannable.setSpan(
            clickable,
            spannable.length - 7,
            spannable.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        binding.signInDescTextView.text = spannable;
        binding.signInDescTextView.movementMethod = LinkMovementMethod.getInstance();
    }
}