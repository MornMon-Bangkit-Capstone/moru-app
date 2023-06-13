package com.capstone.moru.ui.detail.user_routine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ExerciseListItem
import com.capstone.moru.data.api.response.ScheduleDetailListItem
import com.capstone.moru.databinding.ActivityDetailRoutineBinding
import com.capstone.moru.ui.factory.ViewModelFactory

class DetailUserExerciseRoutineActivity : AppCompatActivity() {
    private var _binding: ActivityDetailRoutineBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val detailRoutineViewModel: DetailRoutineViewModel by viewModels { factory }
    private var saveToken: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailRoutineBinding.inflate(layoutInflater)
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
                        detailRoutineViewModel.deleteSchedule(saveToken!!, id)
                        true
                    }
                    R.id.deleteRoutine -> {
                        Log.e("UPDATE", "UPDATE ROUTINE")
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
            detailRoutineViewModel.getExerciseRoutineDetail(saveToken!!, id!!, formattedIsPublic)
            initScheduleDetail(schedule)
        }

        detailRoutineViewModel.exerciseRoutine.observe(this) { routine ->
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

    private fun setRoutineData(listRoutine: ExerciseListItem?) {
        val formatDuration = getString(R.string.default_duration, listRoutine?.durationMin)
        Glide.with(this).load(listRoutine?.visual).into(binding.ivRoutine)
        binding.tvRoutineName.text = listRoutine?.sports
        binding.customCategory.text = listRoutine?.category
        binding.tvDescriptionExercise.text = listRoutine?.description
        binding.tvLocationExercise.text = listRoutine?.location
        binding.tvEquipmentExercise.text = listRoutine?.equipment
        binding.tvDurationExercise.text = formatDuration
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