package com.capstone.moru.ui.add_routine.add_custom_routine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityAddCustomRoutineBinding
import com.capstone.moru.ui.add_routine.add_custom_routine.adapter.AddCustomRoutineSectionsAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AddCustomRoutineActivity : AppCompatActivity() {
    private var _binding: ActivityAddCustomRoutineBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityAddCustomRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val addCustomRoutineSectionsAdapter = AddCustomRoutineSectionsAdapter(this)
        binding.viewPager.adapter = addCustomRoutineSectionsAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager){
            tab,  position -> tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.read,
            R.string.exercise,
        )
    }
}