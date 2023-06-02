package com.capstone.moru.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivitySplashBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.intro.IntroActivity

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
            Handler(Looper.getMainLooper()).postDelayed({
                val intentFromSplash = if (token != "" ) {
                    Intent(this@SplashActivity, MainActivity::class.java)
                } else {
                    Intent(this@SplashActivity, IntroActivity::class.java)
                }
                startActivity(intentFromSplash)
                intentFromSplash.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                finish()
            }, DELAY.toLong())

        }
    }



    companion object {
        const val DELAY = 3000

    }
}