package com.capstone.moru.ui.detail.user_routine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.BookListItem
import com.capstone.moru.data.api.response.ScheduleDetailListItem
import com.capstone.moru.databinding.ActivityDetailUserBookRoutineBinding
import com.capstone.moru.ui.factory.ViewModelFactory

class DetailUserBookRoutineActivity : AppCompatActivity() {
    private var _binding: ActivityDetailUserBookRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val detailRoutineViewModel: DetailRoutineViewModel by viewModels { factory }
    private var saveToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailUserBookRoutineBinding.inflate(layoutInflater)
        factory = ViewModelFactory.getInstance(this)
        setContentView(binding.root)
        setupView()

        val id = intent.getIntExtra(KEY_ID_SCHEDULE, 0)

        binding.ivMenu.setOnClickListener {
            val popUpMenu = PopupMenu(this, binding.ivMenu)
            popUpMenu.menuInflater.inflate(R.menu.routine_detail_menu, popUpMenu.menu)
            popUpMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.updateRoutine -> {
                        Log.e("UPDATE", "UPDATE ROUTINE")
                        true
                    }
                    R.id.deleteRoutine -> {
                        Log.e("DELETE", "DELETE ROUTINE")
                        detailRoutineViewModel.deleteSchedule(saveToken!!, id)
                        finish()
                        true
                    }
                    else -> false
                }
            }
            popUpMenu.show()
        }

        detailRoutineViewModel.getUserToken().observe(this) { token ->
            saveToken = token
            detailRoutineViewModel.getUserScheduleDetail(token, id)
        }

        detailRoutineViewModel.schedule.observe(this) { schedule ->
            val id = schedule?.get(0)?.refId
            val isPublic = schedule?.get(0)?.isPublic
            val formattedIsPublic = if (isPublic == "YES") {
                1
            } else {
                0
            }
            detailRoutineViewModel.getBookRoutineDetail(saveToken!!, id!!, formattedIsPublic)
            initScheduleDetail(schedule)
        }

        detailRoutineViewModel.bookRoutine.observe(this) { routine ->
            setRoutineData(routine)
        }

        detailRoutineViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailRoutineViewModel.message.observe(this){
            displayToast(it)
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setRoutineData(listRoutine: BookListItem?) {
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

    private fun initScheduleDetail(schedule: List<ScheduleDetailListItem?>?) {
        val scheduleItem = schedule?.get(0)
        val formatStatus = when (scheduleItem?.status) {
            "NOT_STARTED" -> {
                "Not started"
            }
            "COMPLETED" -> {
                "Completed"
            }
            else -> {
                "Skipped"
            }
        }

        binding.customStatus.setStatus(scheduleItem?.status!!)
        binding.customStatus.text = formatStatus
        binding.tvDateRoutine.text = scheduleItem?.date
        binding.tvStartTimeRoutine.text = scheduleItem?.startTime
        binding.tvStartTimeRoutine.text = scheduleItem?.endTime
        binding.tvNotesRoutine.text = scheduleItem?.description
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val KEY_ID_SCHEDULE = "key_id_schedule"
    }
}