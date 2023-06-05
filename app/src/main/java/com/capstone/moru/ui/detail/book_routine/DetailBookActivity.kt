package com.capstone.moru.ui.detail.book_routine

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.data.api.response.ListRoutine
import com.capstone.moru.databinding.ActivityDetailBookBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.routines.adapter.RoutineListAdapter.Companion.KEY_BOOK_ROUTINE

class DetailBookActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val detailBookViewModel: DetailBookViewModel by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)
        _binding = ActivityDetailBookBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()

        detailBookViewModel.getUserToken().observe(this) { token ->
            val routineId = intent.getStringExtra(KEY_BOOK_ROUTINE)

            if (routineId != null) {
                detailBookViewModel.getBookRoutineDetail(token, routineId)
            }
        }

        detailBookViewModel.bookRoutine.observe(this) { routine ->
            setRoutineData(routine)
        }

        detailBookViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setRoutineData(listRoutine: ListRoutine) {
        var formatCategory = ""
        val separatedCategory = listRoutine.type?.split(", ")
        if (separatedCategory != null) {
            for (genre in separatedCategory) {
                val secondSeparatedCategory = genre.split("/")
                formatCategory = secondSeparatedCategory[0].trim()
            }
        }


        Glide.with(this).load(listRoutine.imgUrl).into(binding.ivRoutine)
        binding.tvRoutineName.text = listRoutine.title
        binding.customCategory.text = formatCategory
        binding.tvDescriptionBook.text = listRoutine.description
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    companion object {
        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
        const val KEY_BOOK_ROUTINE = "key_book_routine"
    }
}