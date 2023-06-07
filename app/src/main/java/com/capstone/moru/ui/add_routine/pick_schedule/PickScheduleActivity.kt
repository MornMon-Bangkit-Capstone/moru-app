package com.capstone.moru.ui.add_routine.pick_schedule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityPickScheduleBinding

class PickScheduleActivity : AppCompatActivity() {
    private var _binding: ActivityPickScheduleBinding? = null
    private  val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}