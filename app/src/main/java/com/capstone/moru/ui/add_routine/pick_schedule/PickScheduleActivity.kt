package com.capstone.moru.ui.add_routine.pick_schedule

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityPickScheduleBinding
import java.util.*

class PickScheduleActivity : AppCompatActivity() {
    private var _binding: ActivityPickScheduleBinding? = null
    private val binding get() = _binding!!
    val cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityPickScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableEditText()

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.edDate.setOnClickListener {
            binding.edDate.apply {
                isClickable = true
                isLongClickable = false
            }
            dateDialog()
        }

        binding.edStartTime.setOnClickListener {
            binding.edStartTime.apply {
                isClickable = true
                isLongClickable = false
            }

            timeDialog("start")
        }

        binding.edEndTime.setOnClickListener {
            binding.edEndTime.apply {
                isClickable = true
                isLongClickable = false
            }

            timeDialog("end")

        }

        setupView()
    }

    private fun disableEditText(){
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
                    .newEditable("$dayOfMonth - $monthOfYear - $yearPicked")
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
                if (text == "start"){
                    binding.edStartTime.text = Editable.Factory.getInstance()
                        .newEditable("$hourOfDay:$minuteOfDay")
                }else{
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
}