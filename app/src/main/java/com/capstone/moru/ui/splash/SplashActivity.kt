package com.capstone.moru.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivitySplashBinding
import com.capstone.moru.ui.factory.ViewModelFactory

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val splashViewModel: SplashViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        supportActionBar?.hide()

        splashViewModel.getUserToken().observe(this) { token ->
            if (token != "" || token.isEmpty()) {

            }
        }
    }

    companion object {
        const val DELAY = 3000

    }
}