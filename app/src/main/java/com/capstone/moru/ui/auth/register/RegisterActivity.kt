
package com.capstone.moru.ui.auth.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityRegisterBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.fill.FillProfileActivity
import com.capstone.moru.ui.auth.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.tvLogin.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }

        binding.btnRegister.setOnClickListener {
            val intentToFillProfile = Intent(this, FillProfileActivity::class.java)
            startActivity(intentToFillProfile)
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}