package com.capstone.moru.ui.add_routine.pick_schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityPickScheduleBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.factory.ViewModelFactory
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

        pickScheduleViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }


    private fun disableEditText() {
        binding.edDate.inputType = InputType.TYPE_NULL
        binding.edStartTime.inputType = InputType.TYPE_NULL
        binding.edEndTime.inputType = InputType.TYPE_NULL

    }

    private fun dateDialog() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, yearPicked, monthOfYear, dayOfMonth ->
                val dateText = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
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
}