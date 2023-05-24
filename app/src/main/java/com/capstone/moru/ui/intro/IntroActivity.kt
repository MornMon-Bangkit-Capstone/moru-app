package com.capstone.moru.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityIntroBinding
import com.capstone.moru.databinding.ActivitySplashBinding
import com.capstone.moru.ui.login.LoginActivity
import com.capstone.moru.ui.register.RegisterActivity

class IntroActivity : AppCompatActivity() {
    private var _binding: ActivityIntroBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.btnIntroLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }

        binding.btnIntroRegister.setOnClickListener {
            val intentToRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentToRegister)
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}