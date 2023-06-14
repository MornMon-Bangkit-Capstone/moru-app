package com.capstone.moru.ui.fill

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityFillProfileBinding
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

        val items = listOf(
            "Jogging",
            "Running",
            "Jumping Rope",
            "Aerobic",
            "Yoga",
            "Weightlifting",
            "Stationary Biking",
            "Treadmill",
            "Cycling",
            "Dancing",
            "Volleyball",
            "Zumba",
            "Badminton",
            "Table Tennis",
            "Taekwondo",
            "Tennis",
            "Swimming",
            "Basketball",
            "Golf",
            "Football",
            "Handball",
            "Judo",
            "Boxing",
            "Muay Thai",
            "Horseback Riding",
            "Fencing",
            "Hockey",
            "Rugby",
            "Wrestling",
            "Pilates",
            "Wall Climbing",
            "Futsal",
            "Candlestick",
            "Donkey Kickback",
            "Elliptical Training",
            "Crunches",
            "Shoulder Touch",
            "Lunges",
            "Leg Lifts",
            "Stepping",
            "Scissor Kick",
            "Windmill",
            "Calf Raises",
            "Plank",
            "Side Plank",
            "Jumping Jack",
            "Squat",
            "Push-up",
            "Sit-up",
            "Ball Crunch",
            "Tucks Jump",
            "Mountain Climbers",
            "Stair Climbing",
            "Bridge",
            "Gym"
        )

        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.dropdownMenu.setAdapter(adapter)
        binding.dropdownMenu.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.edNameExercise.isHintEnabled = p0.isNullOrEmpty()

            }

        })

        binding.birth.setOnClickListener {
            dateDialog()
        }

        binding.btnNext.setOnClickListener {
            var name = binding.name.text.toString()
            var birthDate = binding.birth.text.toString()
            var goals = binding.edDescgoals.text.toString()
            var favExercise = binding.dropdownMenu.text.toString()
            var favBookName = binding.edFavAuthor.text.toString()

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
                    .newEditable("$yearPicked-$monthOfYear-$dayOfMonth")
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