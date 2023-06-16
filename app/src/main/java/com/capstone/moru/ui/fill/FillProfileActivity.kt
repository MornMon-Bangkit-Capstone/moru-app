package com.capstone.moru.ui.fill

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.moru.R
import com.capstone.moru.databinding.ActivityFillProfileBinding
import com.capstone.moru.ui.MainActivity
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.utils.reduceFileImage
import com.capstone.moru.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.*

class FillProfileActivity : AppCompatActivity() {

    private var _binding: ActivityFillProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val fillProfileViewModel: FillProfileViewModel by viewModels { factory }
    private var getFile: File? = null

    private val cal = Calendar.getInstance()
    private var rating2: Float = 0.0F
    private var rating3: Float = 0.0F
    private var rating4: Float = 0.0F
    private var flag: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFillProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        factory = ViewModelFactory.getInstance(this)
        disableEditText()
        setupView()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                displayToast("Please fill your profile")
            }
        })

        binding.ivProfile.setOnClickListener {
            startGallery()
        }

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
        binding.dropdownMenu.addTextChangedListener(object : TextWatcher {
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

        binding.ratingBar2.setOnRatingBarChangeListener { _, rating, _ ->
            rating2 = rating * 2
        }

        binding.ratingBar3.setOnRatingBarChangeListener { _, rating, _ ->
            rating3 = rating * 2
        }

        binding.ratingBar4.setOnRatingBarChangeListener { _, rating, _ ->
            rating4 = rating * 2
        }

        binding.btnNext.setOnClickListener {
            var name = binding.name.text.toString()
            var birthDate = binding.birth.text.toString()
            var goals = binding.descgoals.text.toString()
            var favExercise = binding.dropdownMenu.text.toString()
            var favBookName = binding.edFavBook.text.toString()
            var favAuthor = binding.edFavAuthor.text.toString()

            if (name.isEmpty() || birthDate.isEmpty() || goals.isEmpty() || favExercise.isEmpty() || favBookName.isEmpty() || favAuthor.isEmpty() || rating2 == 0.0f || rating3 == 0.0f || rating4 == 0.0f || getFile == null) {
                val msg = getString(R.string.fill_field)
                displayToast(msg)
            } else {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    KEY_IMAGE,
                    file.name,
                    requestImageFile
                )

                fillProfileViewModel.getUserId().observe(this) { id ->
                    fillProfileViewModel.getUserToken().observe(this) { token ->
                        fillProfileViewModel.fillProfileUser(token, imageMultipart)
                        fillProfileViewModel.fillUserProfile(
                            token,
                            name,
                            goals,
                            birthDate,
                            favBookName,
                            favExercise,
                            favAuthor
                        )
                        fillProfileViewModel.postBookRating(token, "613496744", rating2.toString())
                        fillProfileViewModel.postBookRating(token, "385504209", rating3.toString())
                        fillProfileViewModel.postBookRating(token, "44023722", rating4.toString())
                    }
                }

                fillProfileViewModel.error.observe(this) {
                    if (!it) {
                        fillProfileViewModel.saveUserFillProfileStatus(1)
                        if (flag) {
                            flag = false
                            val intentToMainActivity = Intent(this, MainActivity::class.java)
                            intentToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intentToMainActivity)
                            finish()
                        }
                    }
                }
            }
        }

        fillProfileViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun dateDialog() {
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, yearPicked, monthOfYear, dayOfMonth ->
                binding.birth.text = Editable.Factory.getInstance()
                    .newEditable("$yearPicked-${monthOfYear + 1}-$dayOfMonth")
                displayToast(binding.birth.text.toString())
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@FillProfileActivity)
                getFile = myFile
                binding.ivProfile.setImageURI(uri)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun disableEditText() {
        binding.birth.inputType = InputType.TYPE_NULL
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnNext.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnNext.isEnabled = true
        }
    }

    private fun displayToast(msg: String) {
        return Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val KEY_IMAGE = "image"
    }
}