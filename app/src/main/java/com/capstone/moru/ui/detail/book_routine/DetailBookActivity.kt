package com.capstone.moru.ui.detail.book_routine

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.databinding.ActivityDetailBookBinding
import com.capstone.moru.ui.add_routine.pick_routine.adapter.PickBookRoutineAdapter
import com.capstone.moru.ui.add_routine.pick_schedule.PickScheduleActivity
import com.capstone.moru.ui.factory.ViewModelFactory

class DetailBookActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val detailBookViewModel: DetailBookViewModel by viewModels { factory }
    private var routineTitle: String? = null
    private var routineType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        factory = ViewModelFactory.getInstance(this)
        _binding = ActivityDetailBookBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()

        detailBookViewModel.getUserToken().observe(this) { token ->
            val routineId = intent.getIntExtra(KEY_BOOK_ROUTINE,1 )
            val isPublic = intent.getIntExtra(KEY_ID_BOOK, 1)

            Log.e("MASUK", routineId.toString())
            detailBookViewModel.getBookRoutineDetail(token, routineId, isPublic)
        }

        detailBookViewModel.bookRoutine.observe(this) { routine ->
            setRoutineData(routine)
        }

        detailBookViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnStart.setOnClickListener {
            val intentToPickSchedule = Intent(this, PickScheduleActivity::class.java)
            intentToPickSchedule.putExtra(KEY_BOOK_ROUTINE, routineTitle)
            intentToPickSchedule.putExtra(KEY_ID_BOOK, routineType)
            startActivity(intentToPickSchedule)
        }
    }

    private fun setRoutineData(listRoutine: BookListItem?) {
        val formattedRoutine = listRoutine?.genres?.substring(
            listRoutine.genres.indexOf("[") + 1,
            listRoutine.genres.indexOf("]")
        )?.split(",")?.map {
            it.trim().replace("^['\"]|['\"]$".toRegex(), "")
        }
        val formattedRating = getString(R.string.default_rating_book, listRoutine?.avgRating)

//        Glide.with(this).load(listRoutine?.imageURLL).into(binding.ivRoutine)
        binding.tvRoutineName.text = listRoutine?.bookTitle
        binding.customCategory.text = formattedRoutine?.firstOrNull()
        binding.tvDescriptionBook.text = listRoutine?.summary
        binding.tvDateBook.text = listRoutine?.yearOfPublication.toString()
        binding.tvRatingBook.text = formattedRating

        routineTitle = listRoutine?.bookTitle
        routineType = "Book"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    companion object {
        const val KEY_BOOK_ROUTINE = "key_book_routine"
        const val KEY_ID_BOOK = "key_id_book"

        const val KEY_BOOK = "key_book"
        const val KEY_BOOK_TITLE = "key_book_title"
    }
}