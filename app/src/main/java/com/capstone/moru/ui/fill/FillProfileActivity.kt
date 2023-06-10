package com.capstone.moru.ui.fill

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.databinding.ActivityFillProfileBinding
import com.capstone.moru.ui.MainActivity
import java.util.*

class FillProfileActivity : AppCompatActivity() {

    private var _binding: ActivityFillProfileBinding? = null
    private val binding get() = _binding!!
    private val cal = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableEditText()
        setupView()

        binding.name.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_NEXT || keyEvent?.keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.descgoals.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }

        binding.btnNext.setOnClickListener {
            val intentToBookPref = Intent(this, FillBookPrefActivity::class.java)
            startActivity(intentToBookPref)
        }

        binding.birth.setOnClickListener {
            dateDialog()
        }

    }

    private fun dateDialog() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, yearPicked, monthOfYear, dayOfMonth ->
                val dateText = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                binding.birth.text = Editable.Factory.getInstance()
                    .newEditable("$dayOfMonth-$monthOfYear-$yearPicked")
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun disableEditText() {
        binding.birth.inputType = InputType.TYPE_NULL
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}