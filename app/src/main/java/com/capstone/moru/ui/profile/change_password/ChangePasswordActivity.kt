package com.capstone.moru.ui.profile.change_password

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityChangePasswordBinding
import com.capstone.moru.databinding.ActivityFillProfileBinding

class ChangePasswordActivity : AppCompatActivity() {

    private var _binding: ActivityChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}