package com.capstone.moru.ui.detail.exercise_routine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ListItem
import com.capstone.moru.data.api.response.ListRoutine
import com.capstone.moru.databinding.ActivityDetailExerciseBinding
import com.capstone.moru.ui.factory.ViewModelFactory

class DetailExerciseActivity : AppCompatActivity() {

    private var _binding: ActivityDetailExerciseBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val detailExerciseViewModel: DetailExerciseViewModel by viewModels { factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)
        _binding = ActivityDetailExerciseBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()

        detailExerciseViewModel.getUserToken().observe(this) { token ->
            val routineId = intent.getStringExtra(KEY_EXERCISE_ROUTINE)

            if (routineId != null) {
                detailExerciseViewModel.getExerciseRoutineDetail(token, routineId)
            }
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
    }

    private fun setRoutineData(listRoutine: List<ListItem?>?) {
        val routines = listRoutine?.get(0)

        var formatCategory = ""
        val separatedCategory = routines?.type?.split(", ")
        if (separatedCategory != null) {
            for (genre in separatedCategory) {
                val secondSeparatedCategory = genre.split("/")
                formatCategory = secondSeparatedCategory[0].trim()
            }
        }

        Glide.with(this).load(routines?.imgUrl).into(binding.ivRoutine)
        binding.tvRoutineName.text = routines?.title
        binding.customCategory.text = formatCategory
        binding.tvDescriptionExercise.text = routines?.description
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    companion object {
        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
    }
}