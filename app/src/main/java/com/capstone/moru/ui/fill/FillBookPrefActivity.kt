package com.capstone.moru.ui.fill

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityFillBookPrefBinding
import com.capstone.moru.databinding.ActivityFillProfileBinding

class FillBookPrefActivity : AppCompatActivity() {

    private var _binding: ActivityFillBookPrefBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillBookPrefBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.btnNext.setOnClickListener {
//            val intentToExercisePref = Intent(this, FillExercisePrefActivity::class.java)
//            startActivity(intentToExercisePref)
//        }
    }
}