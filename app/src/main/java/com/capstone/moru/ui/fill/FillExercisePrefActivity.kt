package com.capstone.moru.ui.fill

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityFillProfileBinding
import com.capstone.moru.ui.auth.login.LoginActivity

class FillExercisePrefActivity : AppCompatActivity() {
    private var _binding: ActivityFillProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intentToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentToLogin)
        }
    }
}