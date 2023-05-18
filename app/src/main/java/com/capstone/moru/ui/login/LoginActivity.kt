package com.capstone.moru.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}