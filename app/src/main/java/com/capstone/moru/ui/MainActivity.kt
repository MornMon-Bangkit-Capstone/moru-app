package com.capstone.moru.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityMainBinding
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val navView: BottomNavigationView = binding.navView
        val navViewController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(
                R.id.homeFragment,
                R.id.scheduleFragment,
                R.id.routinesFragment,
                R.id.statisticFragment,
                R.id.profileFragment
            )
        ).build()

        setupActionBarWithNavController(navViewController, appBarConfiguration)
        navView.setupWithNavController(navViewController)

        binding.fab.setOnClickListener {
            val intentToPickRoutine = Intent(this, PickRoutineActivity::class.java)
            startActivity(intentToPickRoutine)
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}