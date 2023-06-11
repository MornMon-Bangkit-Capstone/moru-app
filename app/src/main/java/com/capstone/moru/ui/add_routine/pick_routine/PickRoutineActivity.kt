package com.capstone.moru.ui.add_routine.pick_routine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.databinding.ActivityPickRoutineBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickRoutineSectionsAdapter
import com.capstone.moru.ui.add_routine.pick_schedule.PickScheduleActivity
import com.google.android.material.tabs.TabLayoutMediator

class PickRoutineActivity : AppCompatActivity() {
    private var _binding: ActivityPickRoutineBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPickRoutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pickRoutineSectionsAdapter = PickRoutineSectionsAdapter(this)
        binding.viewPager.adapter = pickRoutineSectionsAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        binding.backBtnPickRoutine.setOnClickListener {
            finish()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.read,
            R.string.exercise,
        )
    }
}