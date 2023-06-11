package com.capstone.moru.ui.add_routine.pick_schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityPickScheduleBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.factory.ViewModelFactory
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class PickScheduleActivity : AppCompatActivity() {
    private var _binding: ActivityPickScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val pickScheduleViewModel: PickScheduleViewModel by viewModels { factory }
    private val cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPickScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)

        disableEditText()
        setupView()

        fillRoutine()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.edDate.setOnClickListener {
            dateDialog()
        }

        binding.edStartTime.setOnClickListener {
            timeDialog("start")
        }

        binding.edEndTime.setOnClickListener {
            timeDialog("end")
        }

        binding.btnSave.setOnClickListener {
            pickScheduleViewModel.getUserToken().observe(this) { token ->
                val type = binding.edRoutineType.text.toString()
                val routineName = binding.edRoutineName.text.toString()
                val date = binding.edDate.text.toString()
                val startTime = binding.edStartTime.text.toString()
                val endTime = binding.edEndTime.text.toString()
                val notes = binding.edNotes.text.toString()

                Log.e("TIME", "$startTime - $endTime")
//                Log.e("TIME", checkTime(startTime, endTime).toString())
//                else if (checkTime(startTime, endTime)) {
//                val msg = getString(
//                    R.string.incorrect_time
//                )
//                displayToast(msg)
//            }

                if (type.isEmpty() || routineName.isEmpty() || date.isEmpty() || startTime.isEmpty() || endTime.isEmpty() || notes.isEmpty()) {
                    val msg = getString(
                        R.string.fill_field
                    )
                    displayToast(msg)
                } else {
                    pickScheduleViewModel.postUserSchedule(
                        token,
                        type,
                        routineName,
                        date,
                        startTime,
                        endTime,
                        notes
                    )
                    pickScheduleViewModel.error.observe(this) { error ->
                        if (!error) {
                            val msg = getString(
                                R.string.post_schedule_success
                            )
                            displayToast(msg)
                        } else {
                            pickScheduleViewModel.message.observe(this) { message ->
                                val msg = getString(
                                    R.string.post_schedule_failed
                                )
                                displayToast("$msg\n$message")
                            }
                        }
                    }
                }
            }

            pickScheduleViewModel.error.observe(this) {
                if (!it) {
                    val intentToHome = Intent(this, MainActivity::class.java)
                    intentToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intentToHome)
                    finish()
                }
            }
        }

        pickScheduleViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun fillRoutine() {
        val bookType = intent.getStringExtra(KEY_ID_BOOK)
        val exerciseType = intent.getStringExtra(KEY_ID_EXERCISE)
        val bookName = intent.getStringExtra(KEY_BOOK_ROUTINE)
        val exerciseName = intent.getStringExtra(KEY_EXERCISE_ROUTINE)

        if (bookType.isNullOrEmpty()) {
            binding.edRoutineType.setText(exerciseType)
            binding.edRoutineName.setText(exerciseName)
        } else {
            binding.edRoutineType.setText(bookType)
            binding.edRoutineName.setText(bookName)
        }
    }

//    private fun checkTime(startTime: String, endTime: String): Boolean {
//        val formatter = DateTimeFormatter.ofPattern("HH:mm")
//
//        val start = LocalTime.parse(startTime, formatter)
//        val end = LocalTime.parse(endTime, formatter)
//
//        val rangeStart = LocalTime.parse("04.00", formatter)
//        val rangeEnd = LocalTime.parse("10.00", formatter)
//
//        return start.isAfter(rangeStart) && end.isBefore(rangeEnd) && start.isBefore(end) && end.isAfter(
//            start
//        )
//    }


    private fun disableEditText() {
        binding.edDate.inputType = InputType.TYPE_NULL
        binding.edStartTime.inputType = InputType.TYPE_NULL
        binding.edEndTime.inputType = InputType.TYPE_NULL
        binding.edRoutineName.inputType = InputType.TYPE_NULL
        binding.edRoutineType.inputType = InputType.TYPE_NULL
    }

    private fun dateDialog() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, yearPicked, monthOfYear, dayOfMonth ->
                binding.edDate.text = Editable.Factory.getInstance()
                    .newEditable("$dayOfMonth-$monthOfYear-$yearPicked")
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun timeDialog(text: String) {
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { view, hourOfDay, minuteOfDay ->
                if (text == "start") {
                    binding.edStartTime.text = Editable.Factory.getInstance()
                        .newEditable("$hourOfDay:$minuteOfDay")
                } else {
                    binding.edEndTime.text = Editable.Factory.getInstance()
                        .newEditable("$hourOfDay:$minuteOfDay")
                }
            },
            hour,
            minute,
            false
        )

        timePickerDialog.show()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val KEY_BOOK_ROUTINE = "key_book_routine"
        const val KEY_ID_BOOK = "key_id_book"

        const val KEY_EXERCISE_ROUTINE = "key_exercise_routine"
        const val KEY_ID_EXERCISE = "key_id_exercise"
    }
}