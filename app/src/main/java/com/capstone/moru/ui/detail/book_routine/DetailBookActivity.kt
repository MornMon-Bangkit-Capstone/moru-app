package com.capstone.moru.ui.detail.book_routine

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.databinding.ActivityDetailBookBinding
import com.capstone.moru.ui.factory.ViewModelFactory

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
    }

    private fun setRoutineData(listRoutine: BookListItem?) {
//        var formatCategory = ""
//        val separatedCategory = listRoutine?.genres?.split(", ")
//        if (separatedCategory != null) {
//            for (genre in separatedCategory) {
//                val secondSeparatedCategory = genre.split("/")
//                formatCategory = secondSeparatedCategory[0].trim()
//            }
//        }
        val formattedRoutine = listRoutine?.genres?.substring(
            listRoutine.genres.indexOf("[") + 1,
            listRoutine.genres.indexOf("]")
        )?.split(",")?.map {
            it.trim()
        }
        val formattedRating = getString(R.string.default_rating_book, listRoutine?.avgRating)

        Glide.with(this).load(listRoutine?.imageURLL).into(binding.ivRoutine)
        binding.tvRoutineName.text = listRoutine?.bookTitle
        binding.customCategory.text = formattedRoutine?.firstOrNull()
        binding.tvDescriptionBook.text = listRoutine?.summary
        binding.tvDateBook.text = listRoutine?.yearOfPublication.toString()
        binding.tvRatingBook.text = formattedRating


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

    }
}