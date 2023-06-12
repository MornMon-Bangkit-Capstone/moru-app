package com.capstone.moru.ui.detail.user_routine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityDetailRoutineBinding

class DetailRoutineActivity : AppCompatActivity() {
    private var _binding: ActivityDetailRoutineBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

//        binding.ivMenu.setOnClickListener {
//            val popUpMenu =
//        }
    }

    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}