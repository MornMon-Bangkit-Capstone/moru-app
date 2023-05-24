package com.capstone.moru.ui.fill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityFillProfileBinding
import com.capstone.moru.databinding.ActivityRegisterBinding
import com.capstone.moru.ui.MainActivity

class FillProfileActivity : AppCompatActivity() {

    private var _binding: ActivityFillProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.btnNext.setOnClickListener {
            val intentToMain = Intent(this, MainActivity::class.java)
            startActivity(intentToMain)
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}