package com.capstone.moru.ui.subscription

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityGetSubscriptionBinding

class GetSubscriptionActivity : AppCompatActivity() {
    private var _binding: ActivityGetSubscriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGetSubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}