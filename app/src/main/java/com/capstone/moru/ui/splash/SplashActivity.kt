package com.capstone.moru.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityMainBinding
import com.capstone.moru.databinding.ActivitySplashBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.intro.IntroActivity

class SplashActivity : AppCompatActivity() {
    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intentToIntro = Intent(this@SplashActivity, IntroActivity::class.java)
            startActivity(intentToIntro)
            finish()
        }, DELAY.toLong())
    }

    companion object{
        const val DELAY = 3000

    }
}