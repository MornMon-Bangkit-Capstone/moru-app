package com.capstone.moru.ui.profile.profile_data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.capstone.moru.R
import com.capstone.moru.data.api.response.ProfileData
import com.capstone.moru.databinding.ActivityProfileDataBinding
import com.capstone.moru.ui.factory.ViewModelFactory
import com.capstone.moru.ui.profile.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

class ProfileDataActivity : AppCompatActivity() {

    private var _binding: ActivityProfileDataBinding? = null
    private val binding get() = _binding!!

    private lateinit var factory: ViewModelFactory
    private val profileViewModel: ProfileViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityProfileDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        factory = ViewModelFactory.getInstance(this)

        binding.imageView2.setOnClickListener {
            finish()
        }

        profileViewModel.getUserToken().observe(this){
            token -> profileViewModel.getUserProfile(token)
        }

        profileViewModel.profile.observe(this){
            setUserProfile(it)
        }

        profileViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun setUserProfile(it: ProfileData?) {
        binding.tvNameUser.text = it?.username.toString()
        binding.tvBirthUser.text = formatDateString(it?.birthDate!!)
        binding.tvGoalsUser.text = it?.goal
        binding.tvBookUser.text =it?.favBook
        binding.tvExerciseUser.text = it?.favExercise
        binding.tvAuthorUser.text = it?.favAuthor
        Glide.with(this).load(it?.profilePicture).into(binding.ivProfile)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun formatDateString(inputDateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            val date = inputFormat.parse(inputDateString)
            return outputFormat.format(date!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "" // Return an empty string in case of an error
    }
}