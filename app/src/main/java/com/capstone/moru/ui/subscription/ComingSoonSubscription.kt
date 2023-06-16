package com.capstone.moru.ui.subscription

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityComingSoonSubscriptionBinding
import com.capstone.moru.databinding.ActivityGetSubscriptionBinding

class ComingSoonSubscription : AppCompatActivity() {
    private var _binding: ActivityComingSoonSubscriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityComingSoonSubscriptionBinding.inflate(layoutInflater)
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