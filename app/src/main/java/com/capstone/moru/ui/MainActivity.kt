package com.capstone.moru.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityMainBinding
import com.capstone.moru.ui.add_routine.pick_routine.PickRoutineActivity
import com.capstone.moru.ui.alarm.AlarmActivity
import com.capstone.moru.ui.alarm.receiver.AlarmReceiver
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.fill.FillProfileActivity
import com.capstone.moru.ui.home.HomeViewModel
import com.capstone.moru.ui.subscription.GetSubscriptionActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { factory }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)
    }
    private var clicked: Boolean = false
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-M-yyyy")
    private var selectedDate: String? = LocalDate.now().format(formatter)
    private var formatterTime: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private var selectedTime: String? = LocalTime.now().format(formatterTime)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        factory = ViewModelFactory.getInstance(this)

        AlarmReceiver().createNotificationChannel(this)

        homeViewModel.getUserToken().observe(this) { token ->
            homeViewModel.getUserProfile(token)
            homeViewModel.getCurrentSchedule(token, selectedDate!!)
            homeViewModel.profile.observe(this) { profile ->
                if (!token.isNullOrEmpty() && profile.username == null) {
//                    homeViewModel.getFillProfileStatus().observe(this) {
//                        if (it == 2) {
//                            val intentToFillProfile = Intent(this, FillProfileActivity::class.java)
//                            startActivity(intentToFillProfile)
//                        }
//                    }
                    Log.e("PROFILE", "MASUK BERAPA KALI")
                    val intentToFillProfile = Intent(this, FillProfileActivity::class.java)
                    startActivity(intentToFillProfile)

                }
            }
        }

        homeViewModel.schedule.observe(this) { routine ->
            if (routine != null) {
                for (i in routine) {
                    var startTime = LocalTime.parse(i?.startTime).plusMinutes(1)
                    var endTime = startTime.plusMinutes(5)
                    var checkTime = LocalTime.parse(selectedTime)

                    if (((checkTime.isAfter(startTime) && checkTime.isBefore(endTime)) || (startTime == checkTime)) && i?.status == "NOT_STARTED") {
                        val intentAlarmActivity = Intent(this, AlarmActivity::class.java)
                        startActivity(intentAlarmActivity)
                    }
                }
            }
        }

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

        binding.fabMenu.setOnClickListener {
            onFabMenuClicked()
        }

        binding.fabPickRoutine.setOnClickListener {
            val intentToPickRoutine = Intent(this, PickRoutineActivity::class.java)
            startActivity(intentToPickRoutine)
        }

        binding.fabAddRoutine.setOnClickListener {
            val intentToAddRoutine = Intent(this, GetSubscriptionActivity::class.java)
            startActivity(intentToAddRoutine)
        }
    }

    private fun onFabMenuClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabPickRoutine.visibility = View.VISIBLE
            binding.fabAddRoutine.visibility = View.VISIBLE
            binding.tvAddRoutine.visibility = View.VISIBLE
            binding.tvPickRoutine.visibility = View.VISIBLE
        } else {
            binding.fabPickRoutine.visibility = View.INVISIBLE
            binding.fabAddRoutine.visibility = View.INVISIBLE
            binding.tvAddRoutine.visibility = View.INVISIBLE
            binding.tvPickRoutine.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabAddRoutine.startAnimation(fromBottom)
            binding.tvAddRoutine.startAnimation(fromBottom)
            binding.fabPickRoutine.startAnimation(fromBottom)
            binding.tvPickRoutine.startAnimation(fromBottom)
            binding.fabMenu.startAnimation(rotateOpen)
        } else {
            binding.fabAddRoutine.startAnimation(toBottom)
            binding.tvAddRoutine.startAnimation(toBottom)
            binding.fabPickRoutine.startAnimation(toBottom)
            binding.tvPickRoutine.startAnimation(toBottom)
            binding.fabMenu.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean) {
        if (!clicked) {
            binding.fabPickRoutine.isClickable = true
            binding.fabAddRoutine.isClickable = true
        } else {
            binding.fabPickRoutine.isClickable = false
            binding.fabAddRoutine.isClickable = false
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

}