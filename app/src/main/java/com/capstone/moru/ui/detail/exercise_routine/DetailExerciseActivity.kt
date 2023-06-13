package com.capstone.moru.ui.detail.exercise_routine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ExerciseListItem
//import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.api.response.ListRoutine
import com.capstone.moru.databinding.ActivityDetailExerciseBinding
import com.capstone.moru.ui.add_routine.pick_schedule.PickScheduleActivity
import com.capstone.moru.ui.detail.book_routine.DetailBookActivity
import com.capstone.moru.ui.factory.ViewModelFactory

class DetailExerciseActivity : AppCompatActivity() {

    private var _binding: ActivityDetailExerciseBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val detailExerciseViewModel: DetailExerciseViewModel by viewModels { factory }

    private var routineTitle: String? = null
    private var routineType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)
        _binding = ActivityDetailExerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()

        detailExerciseViewModel.getUserToken().observe(this) { token ->
            val routineId = intent.getIntExtra(KEY_EXERCISE_ROUTINE,1)
            val isPublic = intent.getIntExtra(KEY_ID_EXERCISE, 1)

            detailExerciseViewModel.getExerciseRoutineDetail(token, routineId, isPublic)
        }

        detailExerciseViewModel.exerciseRoutine.observe(this) { routine ->
            Log.e("DETAIL EXERCISE", routine.toString())
            setRoutineData(routine)
        }

        detailExerciseViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnStart.setOnClickListener {
            val intentToPickSchedule = Intent(this, PickScheduleActivity::class.java)
            intentToPickSchedule.putExtra(KEY_EXERCISE_ROUTINE, routineType)
            intentToPickSchedule.putExtra(KEY_ID_EXERCISE, routineType)
            startActivity(intentToPickSchedule)
        }
    }

    private fun setRoutineData(listRoutine: ExerciseListItem?) {
        val formatDuration = getString(R.string.default_duration, listRoutine?.durationMin)

        Glide.with(this).load(listRoutine?.visual).into(binding.ivRoutine)
        binding.tvRoutineName.text = listRoutine?.sports
        binding.customCategory.text = listRoutine?.category
        binding.tvDescriptionExercise.text = listRoutine?.description
        binding.tvLocationExercise.text = listRoutine?.location
        binding.tvEquipmentExercise.text = listRoutine?.equipment
        binding.tvDurationExercise.text = formatDuration


        routineTitle = listRoutine?.sports
        routineType = "Exercise"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    companion object {
        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
        const val KEY_ID_EXERCISE = "key_id_exercise"

        const val KEY_EXERCISE = "key_book"
        const val KEY_EXERCISE_TITLE = "key_book_title"
    }
}