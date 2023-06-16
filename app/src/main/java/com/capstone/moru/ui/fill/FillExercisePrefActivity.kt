package com.capstone.moru.ui.fill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityFillProfileBinding

class FillExercisePrefActivity : AppCompatActivity() {
    private var _binding: ActivityFillProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}