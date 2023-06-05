package com.capstone.moru.ui.detail.exercise_routine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityDetailBookBinding
import com.capstone.moru.databinding.ActivityDetailExerciseBinding

class DetailExerciseActivity : AppCompatActivity() {

    private var _binding: ActivityDetailExerciseBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailExerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}